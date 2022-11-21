package utils

import data.Cart
import data.Item
import data.Product
import database.CartTable
import database.UsersTable
import enums.ProductStatus
import interfaces.CartDao
import interfaces.ProductsDao

class CartData: CartDao {

    private lateinit var cartId: String
    private val productsDao: ProductsDao by lazy { ProductsData() }

    override fun createAndGetCartId(): String {
        cartId = CartTable.generateCartId()
        CartTable.carts[cartId] = Cart()
        return cartId
    }

    override fun retrieveCartId(userId: String): String {
        return UsersTable.usersAccountInfo[userId]!!.cartId
    }

    override fun retrieveCartItems(cartId: String): List<Item> {
        return CartTable.carts[cartId]!!.cartItems
    }

    override fun addToCart(cartId: String, product: Product) {
        CartTable.carts[cartId]!!.cartItems.add(Item(productId = product.productId, productName = product.productName, productPrice = product.price, totalPrice = product.price, category = product.category,1, product.status))
    }

    override fun removeFromCart(cartId: String, item: Item) {
        CartTable.carts[cartId]!!.cartItems.remove(item)
    }

    override fun clearCart(cartId: String, cartItems: MutableList<Item>) {
        CartTable.carts[cartId]!!.cartItems.removeAll(cartItems)
    }

    override fun retrieveCartItem(cartId: String, productId: String): Item {
        lateinit var cartItem: Item
        for(item in CartTable.carts[cartId]!!.cartItems) {
            if(item.productId == productId) {
                cartItem = item
                break
            }
        }
        return cartItem
    }

    override fun changeItemQuantityAndPrice(cartId: String, item: Item, quantity: Int) {
        for(cartItem in CartTable.carts[cartId]!!.cartItems) {
            if(cartItem.productId == item.productId) {
                cartItem.quantity = quantity
                cartItem.totalPrice = (quantity * cartItem.productPrice)
                break
            }
        }
    }

    override fun updateSubtotal(cartId: String, subTotal: Float) {
        CartTable.carts[cartId]!!.subTotal = subTotal
    }

    override fun updateAvailableQuantityAndStatusOfCartItems() {
        for((cartId, cart) in CartTable.carts) {
            for(cartItem in cart.cartItems) {
                val availableQuantity = productsDao.retrieveAvailableQuantityOfProduct(cartItem.productId, cartItem.category)
                val status = productsDao.retrieveProductAvailabilityStatus(cartItem.category, cartItem.productId)
                cartItem.status = status
                if(cartItem.quantity > availableQuantity) {
                    changeItemQuantityAndPrice(cartId, cartItem, availableQuantity)
                } else if(status == ProductStatus.OUT_OF_STOCK) {
                    changeItemQuantityAndPrice(cartId, cartItem, 0)
                }
            }
        }
    }
}


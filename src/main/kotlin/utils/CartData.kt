package utils

import data.Cart
import data.Item
import data.Product
import database.CartTable
import database.UsersTable
import enums.ProductStatus

class CartData {

    private lateinit var cartId: String
    private val productsData by lazy { ProductsData() }

    private fun createCart() {
        cartId = CartTable.generateCartId()
        CartTable.carts[cartId] = Cart()
    }

    fun createAndGetCartId(): String {
        createCart()
        return cartId
    }

    fun retrieveCartId(userId: String): String {
        return UsersTable.usersAccountInfo[userId]!!.cartId
    }

    fun retrieveCartItems(cartId: String): List<Item> {
        return CartTable.carts[cartId]!!.cartItems
    }

    fun addToCart(cartId: String, product: Product) {
        CartTable.carts[cartId]!!.cartItems.add(Item(productId = product.productId, productName = product.productName, productPrice = product.price, totalPrice = product.price, category = product.category,1, product.status))
    }

    fun removeFromCart(cartId: String, item: Item) {
        CartTable.carts[cartId]!!.cartItems.remove(item)
    }

    fun clearCart(cartId: String, cartItems: MutableList<Item>) {
        CartTable.carts[cartId]!!.cartItems.removeAll(cartItems)
    }

    fun retrieveCartItem(cartId: String, productId: String): Item {
        lateinit var cartItem: Item
        for(item in CartTable.carts[cartId]!!.cartItems) {
            if(item.productId == productId) {
                cartItem = item
                break
            }
        }
        return cartItem
    }

    fun changeItemQuantityAndPrice(cartId: String, item: Item, quantity: Int) {
        for(cartItem in CartTable.carts[cartId]!!.cartItems) {
            if(cartItem == item) {
                cartItem.quantity = quantity
                cartItem.totalPrice = (quantity * cartItem.productPrice)
                break
            }
        }
    }

    fun updateSubtotal(cartId: String, subTotal: Float) {
        CartTable.carts[cartId]!!.subTotal = subTotal
    }

    fun updateAvailableQuantityAndStatusOfCartItems() {
        for((_, cart) in CartTable.carts) {
            for(cartItem in cart.cartItems) {
                val availableQuantity = productsData.retrieveAvailableQuantityOfProduct(cartItem.productId, cartItem.category)
                val status = productsData.retrieveProductAvailabilityStatus(cartItem.category, cartItem.productId)
                cartItem.status = status
                if(cartItem.quantity > availableQuantity) {
                    cartItem.quantity = availableQuantity
                } else if(status == ProductStatus.OUT_OF_STOCK) {
                    cartItem.quantity = 0
                }
            }
        }
    }

}


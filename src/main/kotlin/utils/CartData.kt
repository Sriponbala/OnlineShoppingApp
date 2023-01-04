package utils

import data.Cart
import data.CartItem
import data.Filters
import data.ProductSku
import database.*
import interfaces.CartDao
import interfaces.ProductsDao

class CartData(private val userName: String = "root",
               private val password: String = "tiger"): CartDao {

    private val productsDao: ProductsDao by lazy { ProductsData() }
    private val database: Database = Database.getConnection(this.userName, this.password)!!

    override fun createAndGetCartId(userId: String): String {
        val cart = Cart(userId)
        database.carts.add(cart)
        return cart.cartId
    }

    override fun retrieveCartItems(cartId: String): MutableList<Triple<CartItem, ProductSku, Filters.StatusFilters>> {
        val cartItems: MutableList<Triple<CartItem, ProductSku, Filters.StatusFilters>> = mutableListOf()
        for(cartItem in database.cartItems) {
            if(cartId == cartItem.cartId) {
                val productDetails = productsDao.retrieveProductDetails(cartItem.skuId)
                val cartItemDetails = Triple(cartItem, productDetails.first, productDetails.second)
                cartItems.add(cartItemDetails)
            }
        }
        return cartItems
    }

    override fun addToCart(cartId: String, skuId: String) { //product: Product, stock: Stock)
        val cartItem = CartItem(cartId = cartId, skuId = skuId)
        database.cartItems.add(cartItem)
    }

    override fun removeFromCart(cartId: String, skuId: String) {
        val iter = database.cartItems.iterator()
        for(it in iter) {
            if(cartId == it.cartId && skuId == it.skuId) {
                iter.remove()
                break
            }
        }
    }

    override fun clearCart(cartId: String) {
        val iter = database.cartItems.iterator()
        while(iter.hasNext()) {
            if(cartId == iter.next().cartId) {
                iter.remove()
            }
        }
    }

    override fun changeItemQuantity(cartId: String, skuId: String, quantity: Int) {
        for(cartItem in database.cartItems) {
            if(cartId == cartItem.cartId && skuId == cartItem.skuId) {
                cartItem.quantity = quantity
                break
            }
        }
    }

    override fun updateSubtotal(cartId: String, subTotal: Float) {
        for(cart in database.carts) {
            if(cartId == cart.cartId) {
                cart.subTotal = subTotal
            }
        }
    }

    override fun getCartItemQuantity(cartId: String, skuId: String): Int {
        var quantity = 0
        for(cartItem in database.cartItems) {
            if(cartId == cartItem.cartId && skuId == cartItem.skuId) {
                quantity = cartItem.quantity
                break
            }
        }
        return quantity
    }

}




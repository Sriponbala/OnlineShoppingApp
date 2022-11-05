package backend

import data.Item
import enums.ProductStatus
import utils.CartData
import utils.ProductsData

class CartActivities {

    private val cartData = CartData()
    private val productsData = ProductsData()
    fun createCart(userId: String) {
        cartData.createCart(userId)
    }

    fun getCartId(userId: String): String {
        return cartData.retrieveCartId(userId)
    }

    fun getCartItems(userId: String, cartId: String): List<Item>? {
        return cartData.retrieveCartItems(userId, cartId)
    }

    fun addToCart(userId: String, category: String, productId: String): Boolean {
        val status = productsData.retrieveProductAvailabilityStatus(category, productId)
        return if(status == ProductStatus.OUT_OF_STOCK) {
            false
        } else {
            cartData.addToCart(userId, category, productId, status)
            true
        }
    }


}
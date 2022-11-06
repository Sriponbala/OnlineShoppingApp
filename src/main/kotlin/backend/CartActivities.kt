package backend

import data.Item
import enums.ProductStatus
import utils.CartData
import utils.ProductsData

class CartActivities {

    private val cartData = CartData()
    private val productsData = ProductsData()

    fun createAndGetCartId(userId: String): String {
        return cartData.addAndGetCartId(userId)
    }

    fun getCartId(userId: String): String {
        val id: String = if(cartData.retrieveCartId(userId) == null) {
            ""
        } else {
            cartData.retrieveCartId(userId)!!
        }
        return id
    }

    fun getCartItems(cartId: String): List<Item>? {
        return cartData.retrieveCartItems(cartId)
    }

    fun addToCart(cartId: String, category: String, productId: String): Boolean {
        val itemAddedToCart: Boolean
        val status = productsData.retrieveProductAvailabilityStatus(category, productId)
        itemAddedToCart = if(status == ProductStatus.OUT_OF_STOCK) {
            false
        } else {
            if(isItemInCart(cartId, productId)) {
                false
            } else {
                cartData.addToCart(cartId, category, productId, status)
                true
            }
        }
        return itemAddedToCart
    }

    private fun isItemInCart(cartId: String, productId: String): Boolean {
        return cartData.checkIfItemIsInCart(cartId, productId)
    }


}
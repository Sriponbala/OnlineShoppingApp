package backend

import data.Product
import utils.WishListsData

class WishListsActivities {

    private val wishListsData = WishListsData()

    fun createAndGetWishListId(userId: String): String {
        return wishListsData.addAndGetWishListId(userId)
    }

    fun getWishListId(userId: String): String {
        val id: String = if(wishListsData.retrieveWishListId(userId) == null) {
            ""
        } else {
            wishListsData.retrieveWishListId(userId)!!
        }
        return id
    }

    fun getWishListProducts(wishListId: String): ArrayList<Product>? {
        return wishListsData.retrieveWishListProducts(wishListId)
    }

    fun addProductToWishList(wishListId: String, category: String, productId: String): Boolean {
        return if(!isProductInWishList(wishListId, productId)) {
            wishListsData.addAProductToWishList(wishListId, category, productId)
            true
        } else {
            false
        }
    }

    fun removeProductFromWishList(wishListId: String, productId: String): Boolean {
        return if(isProductInWishList(wishListId, productId)) {
            wishListsData.deleteProductFromWishList(wishListId, productId)
            true
        } else {
            false
        }
    }

    private fun isProductInWishList(wishListId: String, productId: String): Boolean {
        return wishListsData.checkIfProductIsInUserWishList(wishListId, productId)
    }
}


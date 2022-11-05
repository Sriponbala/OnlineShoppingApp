package backend

import data.Product
import data.WishList
import database.WishListDatabase
import utils.WishListsData

class WishListsActivities {

    private val wishListsData = WishListsData()

    fun createWishList(userId: String) {
        wishListsData.addWishList(userId)
    }

    fun getWishListId(userId: String): String {
        return wishListsData.retrieveWishListId(userId)
    }

    fun getWishListProducts(userId: String, wishListId: String): ArrayList<Product>? {
        return wishListsData.retrieveWishListProducts(userId, wishListId)
    }

    fun addAProductToWishList(userId: String, category: String, productId: String) {
        wishListsData.addAProductToWishList(userId, category, productId)
    }

    fun deleteProductFromWishList(userId: String, productId: String) {
        wishListsData.deleteProductFromWishList(userId, productId)
    }

    fun isProductInWishList(userId: String, productId: String): Boolean {
        return wishListsData.checkIfProductIsInUserWishList(userId, productId)
    }
}


package backend

import data.Product
import utils.ProductsData
import utils.Utility
import utils.WishListsData

class WishListsActivities {

    private val wishListsData = WishListsData()
    private val productsData = ProductsData()
    private val utility = Utility()

    fun createAndGetWishListId(userId: String): String {
        return if(utility.checkIfUserExists(userId)) {
            wishListsData.addAndGetWishListId()
        } else ""
    }

    fun getWishListId(userId: String): String {
        val id: String = if(wishListsData.retrieveWishListId(userId) == null) {
            ""
        } else {
            wishListsData.retrieveWishListId(userId)!!
        }
        return id
    }

    fun getWishListProducts(wishListId: String): ArrayList<Product> {
        return if(utility.checkIfWishListExists(wishListId)) {
            wishListsData.retrieveWishListProducts(wishListId)
        } else arrayListOf()
    }

    fun addProductToWishList(wishListId: String, category: String, productId: String): Boolean {
        return if(utility.checkIfCategoryExistsInProductDB(category)) {
            if(utility.checkIfProductExists(productId, category)) {
                if(utility.checkIfWishListExists(wishListId)) {
                    if(!utility.checkIfProductIsInUserWishList(wishListId, productId)) {
                        val product = productsData.retrieveProduct(productId, category)
                        wishListsData.addAProductToWishList(wishListId, product)
                        true
                    } else false
                } else false
            } else false
        } else false
    }

    fun removeProductFromWishList(wishListId: String, productId: String): Boolean {
        return if(utility.checkIfWishListExists(wishListId)) {
            if(utility.checkIfProductIsInUserWishList(wishListId, productId)) {
                val product = wishListsData.retrieveProductFromWishList(wishListId, productId)
                wishListsData.deleteProductFromWishList(wishListId, product)
                true
            } else false
        } else false
    }

}


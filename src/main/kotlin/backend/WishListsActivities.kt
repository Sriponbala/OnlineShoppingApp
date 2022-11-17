package backend

import data.Product
import interfaces.UtilityDao
import interfaces.WishListDao
import utils.ProductsData
import utils.WishListsData

class WishListsActivities(private val utility: UtilityDao) {

    private val wishListsDao: WishListDao = WishListsData()
    private val productsData = ProductsData()

    fun createAndGetWishListId(userId: String): String {

        return if(utility.checkIfUserExists(userId)) {
            wishListsDao.addAndGetWishListId()
        } else ""
    }

    fun getWishListProducts(wishListId: String): ArrayList<Product> {

        return if(utility.checkIfWishListExists(wishListId)) {
            wishListsDao.retrieveWishListProducts(wishListId)
        } else arrayListOf()
    }

    fun addProductToWishList(wishListId: String, category: String, productId: String): Boolean {

        return if(utility.checkIfCategoryExistsInProductDB(category)) {
            if(utility.checkIfProductExists(productId, category)) {
                if(utility.checkIfWishListExists(wishListId)) {
                    if(!utility.checkIfProductIsInUserWishList(wishListId, productId)) {
                        val product = productsData.retrieveProduct(productId, category)
                        wishListsDao.addAProductToWishList(wishListId, product)
                        true
                    } else false
                } else false
            } else false
        } else false
    }

    fun removeProductFromWishList(wishListId: String, productId: String): Boolean {

        return if(utility.checkIfWishListExists(wishListId)) {
            if(utility.checkIfProductIsInUserWishList(wishListId, productId)) {
                val product = wishListsDao.retrieveProductFromWishList(wishListId, productId)
                wishListsDao.deleteProductFromWishList(wishListId, product)
                true
            } else false
        } else false
    }

}


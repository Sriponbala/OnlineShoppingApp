package backend

import data.ProductSku
import interfaces.UtilityDao
import interfaces.WishListDao

class WishListsActivities(private val utility: UtilityDao, private val wishListsDao: WishListDao) {

    fun createAndGetWishListId(userId: String): String {
        return wishListsDao.createAndGetWishListId(userId)
    }

    fun getWishListProducts(wishListId: String): ArrayList<ProductSku> {
        return if(utility.checkIfWishListExists(wishListId)) {
            wishListsDao.retrieveWishListProducts(wishListId)
        } else arrayListOf()
    }

    fun addProductToWishList(wishListId: String, skuId: String): Boolean {
        return if(utility.checkIfWishListExists(wishListId)) {
            if(!utility.checkIfProductIsInUserWishList(wishListId, skuId)) {
                wishListsDao.addAProductToWishList(wishListId, skuId)
                true
            } else false
        } else false
    }

    fun removeProductFromWishList(wishListId: String, skuId: String): Boolean {
        return if(utility.checkIfWishListExists(wishListId)) {
            if(utility.checkIfProductIsInUserWishList(wishListId, skuId)) {
                wishListsDao.deleteProductFromWishList(wishListId, skuId)
                true
            } else false
        } else false
    }

}


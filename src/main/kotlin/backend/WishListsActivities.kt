package backend

import data.Product
import utils.WishListsData

class WishListsActivities(private val userId: String) {

    private val wishListsData = WishListsData(userId = this.userId)


    fun getWishListProducts(): ArrayList<Product>? {
        return wishListsData.retrieveWishListProducts()
    }

    fun addAProductToWishList(product: Product) {
        wishListsData.addAProductToWishList(product)
    }

//    fun createWishList() {
//        wishListsData.addWishList(WishList())
//    }

    fun deleteProductFromWishList(productId: String) {
        wishListsData.deleteProductFromWishList(productId)
    }

}


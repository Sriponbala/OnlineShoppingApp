package utils

import data.Product
import data.WishList
import database.WishListTable
import interfaces.WishListDao

class WishListsData: WishListDao {

    private lateinit var wishListId: String

    override fun retrieveWishListProducts(wishListId: String): ArrayList<Product> {
        return WishListTable.usersWishList[wishListId]!!.wishListProducts
    }

    override fun addAProductToWishList(wishListId: String, product: Product) {
        WishListTable.usersWishList[wishListId]!!.wishListProducts.add(product)
    }

    override fun deleteProductFromWishList(wishListId: String, product: Product) {
        WishListTable.usersWishList[wishListId]!!.wishListProducts.remove(product)
    }

    override fun addAndGetWishListId(): String {
        createWishList()
        return wishListId
    }

    private fun createWishList() {
        wishListId = WishListTable.generateWishListId()
        WishListTable.usersWishList[wishListId] = WishList(wishListId = wishListId)
    }

    override fun retrieveProductFromWishList(wishListId: String, productId: String): Product {
        lateinit var wishListProduct: Product
        for(product in WishListTable.usersWishList[wishListId]!!.wishListProducts) {
            if(product.productId == productId) {
                wishListProduct = product
                break
            }
        }
        return wishListProduct
    }

}
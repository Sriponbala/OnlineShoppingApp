package utils

import data.Product
import data.WishList
import database.WishListTable

class WishListsData {

//    private late init var wishListDatabase: WishListDatabase
//    private late init var productsDatabase: ProductsDatabase
//
//    private fun getConnection() {
//        wishListDatabase = WishListDatabase.getInstance(dbUserName, dbPassword)!!
//        println("wishlistDb: $wishListDatabase")
//        productsDatabase = ProductsDatabase.getInstance(dbUserName, dbPassword)!!
//    }
//
//    init {
//        getConnection()
//    }

    private lateinit var wishListId: String

    fun retrieveWishListProducts(wishListId: String): ArrayList<Product> {
        return WishListTable.usersWishList[wishListId]!!.wishListProducts
    }

    fun addAProductToWishList(wishListId: String, product: Product) {
        WishListTable.usersWishList[wishListId]!!.wishListProducts.add(product)
    }

    fun deleteProductFromWishList(wishListId: String, product: Product) {
        WishListTable.usersWishList[wishListId]!!.wishListProducts.remove(product)
    }

    fun addAndGetWishListId(): String {
        createWishList()
        return wishListId
    }

    private fun createWishList() {
        wishListId = WishListTable.generateWishListId()
        WishListTable.usersWishList[wishListId] = WishList(wishListId = wishListId)
    }

    fun retrieveProductFromWishList(wishListId: String, productId: String): Product {
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
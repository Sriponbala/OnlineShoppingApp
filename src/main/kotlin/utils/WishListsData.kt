package utils

import data.Product
import data.WishList
import database.ProductsDatabase
import database.UsersDatabase
import database.WishListDatabase

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

    fun retrieveWishListId(userId: String): String? {
        var id: String? = null
        if(UsersDatabase.users.containsKey(userId)) {
            if(UsersDatabase.usersAccountInfo.containsKey(userId)) {
                id = UsersDatabase.usersAccountInfo[userId]?.wishListId
            }
        }
       return id
    }

    fun retrieveWishListProducts(wishListId: String): ArrayList<Product> {
        return WishListDatabase.usersWishList[wishListId]!!.wishListProducts
    }

    fun addAProductToWishList(wishListId: String, product: Product) {
        WishListDatabase.usersWishList[wishListId]!!.wishListProducts.add(product)
    }

    fun deleteProductFromWishList(wishListId: String, product: Product) {
        WishListDatabase.usersWishList[wishListId]!!.wishListProducts.remove(product)
    }

    fun addAndGetWishListId(): String {
        createWishList()
        return wishListId
    }

    private fun createWishList() {
        wishListId = WishListDatabase.generateWishListId()
        WishListDatabase.usersWishList[wishListId] = WishList(wishListId = wishListId)
    }

    fun retrieveProductFromWishList(wishListId: String, productId: String): Product {
        lateinit var wishListProduct: Product
        for(product in WishListDatabase.usersWishList[wishListId]!!.wishListProducts) {
            if(product.productId == productId) {
                wishListProduct = product
                break
            }
        }
        return wishListProduct
    }

}
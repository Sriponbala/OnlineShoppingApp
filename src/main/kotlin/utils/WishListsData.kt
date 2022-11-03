package utils

import data.Product
import data.WishList
import database.WishListDatabase

class WishListsData(private val dbUserName: String = "root",
                    private val dbPassword: String = "tiger",
                    private val userId: String) {

    private lateinit var wishListDatabase: WishListDatabase

    private fun getConnection() {
        wishListDatabase = WishListDatabase.getInstance(dbUserName, dbPassword)!!
    }

    init {
        getConnection()
        addWishList()
    }

    fun retrieveWishListProducts(): ArrayList<Product>? {
        return wishListDatabase.getWishList(userId)?.wishListProducts
    }

    fun addAProductToWishList(product: Product) {
        wishListDatabase.addProductToWishListOfUser(userId, product)
    }

    fun deleteProductFromWishList(productId: String) {
        wishListDatabase.deleteProductFromWishList(userId, productId)
    }

    private fun addWishList() {
        wishListDatabase.addWishList(userId, WishList())
    }
}
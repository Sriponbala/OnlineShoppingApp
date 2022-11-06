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

    fun retrieveWishListProducts(wishListId: String): ArrayList<Product>? {
        return if(WishListDatabase.usersWishList.containsKey(wishListId)) {
            WishListDatabase.usersWishList[wishListId]?.wishListProducts
        } else null
    }

    fun addAProductToWishList(wishListId: String, category: String, productId: String) {
        if(ProductsDatabase.products.containsKey(category)) {
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    if(WishListDatabase.usersWishList.containsKey(wishListId)) {
                        WishListDatabase.usersWishList[wishListId]?.wishListProducts?.add(product)
                        break
                    }
                }
            }
        }
    }

    fun deleteProductFromWishList(wishListId: String, productId: String) {
        if(WishListDatabase.usersWishList.containsKey(wishListId)) {
            for(product in WishListDatabase.usersWishList[wishListId]?.wishListProducts!!) {
                if(product.productId == productId) {
                    WishListDatabase.usersWishList[wishListId]?.wishListProducts?.remove(product)
                    break
                }
            }
        }
    }

    fun addAndGetWishListId(userID: String): String {
        var id = ""
        if(UsersDatabase.users.containsKey(userID)) {
            createWishList()
            id = wishListId
        }
        return id
    }

    private fun createWishList() {
        wishListId = WishListDatabase.generateWishListId()
        WishListDatabase.usersWishList[wishListId] = WishList(wishListId = wishListId)
    }

    fun checkIfProductIsInUserWishList(wishListId: String, productId: String): Boolean {
        var isProductInWishList = false
        if(WishListDatabase.usersWishList.containsKey(wishListId)) {
            for(product in WishListDatabase.usersWishList[wishListId]?.wishListProducts!!) {
                if(product.productId == productId) {
                    isProductInWishList = true
                    break
                }
            }
        }
        return isProductInWishList
    }

}
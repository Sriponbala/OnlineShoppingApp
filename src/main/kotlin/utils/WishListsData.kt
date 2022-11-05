package utils

import data.Product
import data.WishList
import database.ProductsDatabase
import database.UsersDatabase
import database.WishListDatabase
import userInterface.WishListPage

class WishListsData(private val dbUserName: String = "root",
                    private val dbPassword: String = "tiger") {

//    private lateinit var wishListDatabase: WishListDatabase
//    private lateinit var productsDatabase: ProductsDatabase
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

    private var wishListId = 1
    private fun generateWishListId(): String {
        return "WL${wishListId++}"
    }

    fun retrieveWishListId(userId: String): String {
        return if(WishListDatabase.usersWishList.containsKey(userId)) {
            WishListDatabase.usersWishList[userId]?.wishListId as String
        } else ""
    }

    fun retrieveWishListProducts(userId: String, wishListId: String): ArrayList<Product>? {
        return if(UsersDatabase.users.containsKey(userId) && WishListDatabase.usersWishList[userId]?.wishListId == wishListId) {
            WishListDatabase.usersWishList[userId]?.wishListProducts
        } else null
    }

    fun addAProductToWishList(userId: String, category: String, productId: String) {
        if(ProductsDatabase.products.containsKey(category)) {
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    if(UsersDatabase.users.containsKey(userId)) {
                        val wishListId = retrieveWishListId(userId)
                        if(WishListDatabase.usersWishList[userId]?.wishListId == wishListId) {
                            WishListDatabase.usersWishList[userId]?.wishListProducts?.add(product)
                            break
                        }
                    }
                }
            }
        }
    }

    fun deleteProductFromWishList(userId: String, productId: String) {
        if(UsersDatabase.users.containsKey(userId)) {
            val wishListId = retrieveWishListId(userId)
            if(WishListDatabase.usersWishList[userId]?.wishListId == wishListId) {
                for(product in WishListDatabase.usersWishList[userId]?.wishListProducts!!) {
                    if(product.productId == productId) {
                        WishListDatabase.usersWishList[userId]?.wishListProducts?.remove(product)
                        break
                    }
                }
            }
        }
    }

    fun addWishList(userID: String) {
        if(UsersDatabase.users.containsKey(userID)) {
            WishListDatabase.usersWishList[userID] = WishList(wishListId = generateWishListId())
        }
    }

    fun checkIfProductIsInUserWishList(userId: String, productId: String): Boolean {
        var isProductInWishList = false
        if(WishListDatabase.usersWishList.containsKey(userId)) {
            for(product in WishListDatabase.usersWishList[userId]?.wishListProducts!!) {
                if(product.productId == productId) {
                    isProductInWishList = true
                    break
                }
            }
        }
        return isProductInWishList
    }

}
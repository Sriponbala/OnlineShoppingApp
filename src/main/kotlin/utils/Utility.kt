package utils

import data.User
import database.*

class Utility {

    private lateinit var users: MutableMap<String, User>
    fun checkUniqueUser(mobile: String): Boolean {
        users = UsersDatabase.users
        println("Users: $users")
        var flag = false
        if (users.isEmpty()) {
            flag = true
        } else {
            for((_,user) in users) {
                if (mobile == user.userMobile) {
                    flag = false
                    break
                } else {
                    flag = true
                }
            }
        }
        return flag
    }

    fun validateLoginCredentials(mobile: String, password: String) : Boolean {
        this.users = UsersDatabase.users
        println("Users: login() : $users")
        var flag = false
        for((userId,user) in users) {
            if (mobile == user.userMobile&& password == (getPassword(userId))) {
                flag = true
                break
            }
        }
        return flag
    }

    private fun getPassword(userId: String): String {
        var password = ""
        UsersDatabase.usersPassword[userId]?.let {
            password = it.password
        }
        return password
    }

    fun checkIfUserExists(userId: String): Boolean {
        return UsersDatabase.users.containsKey(userId)
    }

    fun checkIfUserAccountInfoExists(userId: String): Boolean {
        return UsersDatabase.usersAccountInfo.containsKey(userId)
    }

    fun checkIfWishListExists(wishListId: String): Boolean {
        return WishListDatabase.usersWishList.containsKey(wishListId)
    }

    fun checkIfProductIsInUserWishList(wishListId: String, productId: String): Boolean {
        var isProductInWishList = false
        if(checkIfWishListExists(wishListId)) {
            for(product in WishListDatabase.usersWishList[wishListId]!!.wishListProducts) {
                if(product.productId == productId) {
                    isProductInWishList = true
                    break
                }
            }
        }
        return isProductInWishList
    }

    fun checkIfCategoryExistsInProductDB(category: String): Boolean {
        return ProductsDatabase.products.containsKey(category)
    }

    fun checkIfProductExists(productId: String, category: String): Boolean {
        var isProductExists = false
        if(checkIfCategoryExistsInProductDB(category)) {
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    isProductExists = true
                    break
                }
            }
        }
        return isProductExists
    }

    fun checkIfOrdersHistoryExists(ordersHistoryId: String): Boolean {
        return OrdersDatabase.usersOrdersHistory.containsKey(ordersHistoryId)
    }

    fun checkIfCartExists(cartId: String): Boolean {
        return CartDatabase.carts.containsKey(cartId)
    }

    fun checkIfItemIsInCart(cartId: String, productId: String): Boolean {
        var isItemInCart = false
        for(item in CartDatabase.carts[cartId]!!.cartItems) {
            if(item.productId == productId) {
                isItemInCart = true
                break
            }
        }
        return isItemInCart
    }

}

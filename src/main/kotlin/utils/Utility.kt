package utils

import data.User
import database.*
import interfaces.UtilityDao

class Utility: UtilityDao {

    private lateinit var users: MutableMap<String, User>
    override fun checkUniqueUser(mobile: String): Boolean {
        users = UsersTable.users
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

    override fun validateLoginCredentials(mobile: String, password: String) : Boolean {
        this.users = UsersTable.users
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
        UsersTable.users[userId]?.userPassword?.let{
            password = it.password
        }
        return password
    }

    override fun checkIfUserExists(userId: String): Boolean {
        return UsersTable.users.containsKey(userId)
    }

    override fun checkIfUserAccountInfoExists(userId: String): Boolean {
        return UsersTable.usersAccountInfo.containsKey(userId)
    }

    override fun checkIfWishListExists(wishListId: String): Boolean {
        return WishListTable.usersWishList.containsKey(wishListId)
    }

    override fun checkIfProductIsInUserWishList(wishListId: String, productId: String): Boolean {
        var isProductInWishList = false
        if(checkIfWishListExists(wishListId)) {
            for(product in WishListTable.usersWishList[wishListId]!!.wishListProducts) {
                if(product.productId == productId) {
                    isProductInWishList = true
                    break
                }
            }
        }
        return isProductInWishList
    }

    override fun checkIfCategoryExistsInProductDB(category: String): Boolean {
        return ProductsTable.products.containsKey(category)
    }

    override fun checkIfProductExists(productId: String, category: String): Boolean {
        var isProductExists = false
        if(checkIfCategoryExistsInProductDB(category)) {
            for(product in ProductsTable.products[category]!!) {
                if(product.productId == productId) {
                    isProductExists = true
                    break
                }
            }
        }
        return isProductExists
    }

    override fun checkIfOrdersHistoryExists(ordersHistoryId: String): Boolean {
        return OrdersTable.usersOrdersHistory.containsKey(ordersHistoryId)
    }

    override fun checkIfCartExists(cartId: String): Boolean {
        return CartTable.carts.containsKey(cartId)
    }

    override fun checkIfItemIsInCart(cartId: String, productId: String): Boolean {
        var isItemInCart = false
        for(item in CartTable.carts[cartId]!!.cartItems) {
            if(item.productId == productId) {
                isItemInCart = true
                break
            }
        }
        return isItemInCart
    }

}

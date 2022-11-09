package utils

import data.AccountInfo
import data.User
import data.UserPassword
import database.UsersTable

class UserData {

//    private late init var usersDatabase: UsersDatabase
//
//    private fun getConnection() {
//        usersDatabase = UsersDatabase.getInstance(dbUserName, dbPassword)!!
//    }
//
//    init {
//        getConnection()
//    }

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String {
        val userId = UsersTable.generateUserId()
        val user = User(userId, userName, userMobile,userEmail, UserPassword(userId, password))
        UsersTable.users[user.userId] = user
        return user.userId
    }

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String) {
        UsersTable.usersAccountInfo[userId] = AccountInfo(cartId, wishListId, ordersHistoryId)
    }

    fun retrieveAccountInfo(userId: String): AccountInfo {
        return UsersTable.usersAccountInfo[userId]!!
    }

    fun retrieveUserId(mobile: String): String {
        var id = ""
        for((userId, user) in UsersTable.users) {
            if(mobile == user.userMobile) {
                id = userId
                break
            }
        }
        return id
    }

    fun retrieveUser(userId: String): User {
        return UsersTable.users[userId]!!.copy(userPassword = null)
    }

}
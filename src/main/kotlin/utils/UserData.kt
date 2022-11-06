package utils

import data.AccountInfo
import data.User
import data.UserPassword
import database.UsersDatabase

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
        val user = User(UsersDatabase.generateUserId(), userName, userMobile,userEmail)
        UsersDatabase.users[user.userId] = user
        val userPassword = UserPassword(user.userId, password)
        UsersDatabase.usersPassword[user.userId] = userPassword
        return user.userId
    }

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String) {
        if(UsersDatabase.users.containsKey(userId)) {
            UsersDatabase.usersAccountInfo[userId] = AccountInfo(cartId, wishListId, ordersHistoryId)
        }
    }

    fun retrieveAccountInfo(userId: String): AccountInfo? {
        val accountInfo: AccountInfo? = if(UsersDatabase.usersAccountInfo.containsKey(userId)) {
            UsersDatabase.usersAccountInfo[userId]
        } else null
        return accountInfo
    }

    fun retrieveUserId(mobile: String): String {
        var id = ""
        for((userId, user) in UsersDatabase.users) {
            if(mobile == user.userMobile) {
                id = userId
                break
            }
        }
        return id
    }


    fun retrieveUser(userId: String): User? {
        var userData: User? = null
        if(UsersDatabase.users.containsKey(userId)) {
            userData = UsersDatabase.users[userId]
        }
        return userData
    }


}
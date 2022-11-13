package utils

import data.AccountInfo
import data.User
import data.UserPassword
import database.UsersTable
import interfaces.UserDao

class UserData: UserDao {

    override fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String {
        val userId = UsersTable.generateUserId()
        val user = User(userId, userName, userMobile,userEmail, UserPassword(userId, password))
        UsersTable.users[user.userId] = user
        return user.userId
    }

    override fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String) {
        UsersTable.usersAccountInfo[userId] = AccountInfo(cartId, wishListId, ordersHistoryId)
    }

    override fun retrieveAccountInfo(userId: String): AccountInfo {
        return UsersTable.usersAccountInfo[userId]!!
    }

    override fun retrieveUserId(mobile: String): String {
        var id = ""
        for((userId, user) in UsersTable.users) {
            if(mobile == user.userMobile) {
                id = userId
                break
            }
        }
        return id
    }

    override fun retrieveUser(userId: String): User {
        return UsersTable.users[userId]!!.copy(userPassword = null)
    }

}
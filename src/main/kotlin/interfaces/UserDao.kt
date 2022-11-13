package interfaces

import data.AccountInfo
import data.User

interface UserDao {

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String)

    fun retrieveAccountInfo(userId: String): AccountInfo

    fun retrieveUserId(mobile: String): String

    fun retrieveUser(userId: String): User
}
package database

import data.AccountInfo
import data.User

object UsersTable {

    val users: MutableMap<String, User> = mutableMapOf() // userId, User
    val usersAccountInfo: MutableMap<String, AccountInfo> = mutableMapOf() // userId, AccountInfo

    private var userId = 1

    fun generateUserId(): String {
        return "USER${userId++}"
    }
}



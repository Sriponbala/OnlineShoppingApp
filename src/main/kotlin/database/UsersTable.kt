package database

import data.AccountInfo
import data.User
import data.UserPassword

object UsersTable {

    val users: MutableMap<String, User> = mutableMapOf() // userId, User
    val usersAccountInfo: MutableMap<String, AccountInfo> = mutableMapOf() // userId, AccountInfo

    fun dummy() {
        println(users)
    }

    private var userId = 1

    fun generateUserId(): String {
        return "USER${userId++}"
    }
}




/*
    fun addUser(user: User) {
        users[user.userId] = user
    }

    fun addPassword(userId: String, password: UserPassword) {
        usersPassword[userId] = password
    }

    fun getUserId(mobile: String): String {
        var id = ""
        for((userId, user) in users) {
            if(mobile == user.userMobile) {
                id = userId
                break
            }
        }
        return id
    }

    fun getUser(mobile: String): User? {
        var userData: User? = null
        for((_, user) in users) {
            if(mobile == user.userMobile) {
                userData = user
                break
            } else {
                userData = null
            }
        }
        return userData
    }

 */
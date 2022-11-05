package database

import data.User
import data.UserPassword

object UsersDatabase {

    val users: MutableMap<String, User> = mutableMapOf()
    val usersPassword: MutableMap<String, UserPassword> = mutableMapOf()

    fun dummy() {
        println(users)
    }

    private var userId = 1

    fun generateUserId(): String {
        return "USER${userId++}"
    }
}


//class UsersDatabase private constructor() {
//
//
//
//    companion object {
//
//        private const val USERNAME = "root"
//        private const val PASSWORD = "tiger"
//        fun getInstance(userName: String, password: String): UsersDatabase? {
//
//            return if (userName == USERNAME && password == PASSWORD) {
//                UsersDatabase()
//            } else {
//                null
//            }
//        }
//    }


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
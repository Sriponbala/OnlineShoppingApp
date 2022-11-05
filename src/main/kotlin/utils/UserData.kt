package utils

import data.User
import data.UserPassword
import database.UsersDatabase

class UserData(private val dbUserName: String = "root",
               private val dbPassword: String = "tiger") {

//    private lateinit var usersDatabase: UsersDatabase
//
//    private fun getConnection() {
//        usersDatabase = UsersDatabase.getInstance(dbUserName, dbPassword)!!
//    }
//
//    init {
//        getConnection()
//    }

    private var userId = 1

    private fun generateUserId(): String {
        return "USER${userId++}"
    }

    fun createUserAccount(userName: String, userMobile: String, userEmail: String, password: String) {
        val user = User(generateUserId(), userName, userMobile,userEmail)
        UsersDatabase.users[user.userId] = user
        val userPassword = UserPassword(user.userId, password)
        UsersDatabase.usersPassword[user.userId] = userPassword
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


    fun retrieveUser(mobile: String): User? {
        var userData: User? = null
        for((_, user) in UsersDatabase.users) {
            if(mobile == user.userMobile) {
                userData = user
                break
            } else {
                userData = null
            }
        }
        return userData
    }


}
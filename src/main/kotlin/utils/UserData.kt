package utils

import data.User
import data.UserPassword
import database.UsersDatabase

class UserData(private val dbUserName: String = "root",
               private val dbPassword: String = "tiger") {

    private lateinit var usersDatabase: UsersDatabase

    private fun getConnection() {
        usersDatabase = UsersDatabase.getInstance(dbUserName, dbPassword)!!
    }

    init {
        getConnection()
    }

    fun createAccount(userId: String, userName: String, userMobile: String, userEmail: String, password: String) {
        val user = User(userId, userName, userMobile,userEmail)
        usersDatabase.addUser(user)
        val userPassword = UserPassword(userId, password)
        usersDatabase.addPassword(userId, userPassword)
    }

     fun retrieveUserId(mobile: String): String {
         return usersDatabase.getUserId(mobile)
    }

    fun retrieveUser(mobile: String): User? {
        return usersDatabase.getUser(mobile)
    }


}
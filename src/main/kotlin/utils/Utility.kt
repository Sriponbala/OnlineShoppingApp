package utils

import data.UsersDatabase
import data.User
import data.UserPassword

class Utility(private val dbUserName: String = "root",
              private val dbPassword: String = "tiger") {

    private lateinit var usersDatabase: UsersDatabase
    private lateinit var users: MutableMap<String, User>
    private lateinit var usersPassword: MutableMap<String, UserPassword>
    private fun getConnection() {
        usersDatabase = UsersDatabase.getInstance(dbUserName, dbPassword)!!
    }
    init {
        getConnection()
    }

    fun checkUniqueUser(mobile: String): Boolean {
        users = usersDatabase.getUsers()
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

    fun validateLoginCredentials(mobile: String, password: String) : Boolean {
        users = usersDatabase.getUsers()
        println("Users: login() : $users")
        var flag = false
        for((userId,user) in users) {
            if (mobile == user.userMobile&& password == (getPassword(userId) as String)) {
                flag = true
                break
            }
        }
        return flag
    }

    private fun getPassword(userId: String): String? {
        usersPassword = usersDatabase.getUsersPassword()
        return usersPassword[userId]?.password
    }

}
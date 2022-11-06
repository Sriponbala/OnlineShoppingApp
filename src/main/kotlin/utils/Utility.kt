package utils

import database.UsersDatabase
import data.User

class Utility {

    private lateinit var users: MutableMap<String, User>
    fun checkUniqueUser(mobile: String): Boolean {
        users = UsersDatabase.users
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
        users = UsersDatabase.users
        println("Users: login() : $users")
        var flag = false
        for((userId,user) in users) {
            if (mobile == user.userMobile&& password == (getPassword(userId))) {
                flag = true
                break
            }
        }
        return flag
    }

    private fun getPassword(userId: String): String {
        var password = ""
        UsersDatabase.usersPassword[userId]?.let {
            password = it.password
        }
        return password
    }

}
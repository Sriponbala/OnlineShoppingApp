package utils

import data.Database
import data.User

object Utility {

    private lateinit var users: MutableMap<String, User> //= Database.getUsers()

    fun checkUniqueUser(email: String): Boolean {
        users = Database.getUsers()
        println("Users: $users")
        var flag = false
        if (users.isEmpty()) {
            flag = true
        } else {
            for((_,user) in users) {
                if (email == user.userEmail) {
                    flag = false
                    break
                } else {
                    flag = true
                }
            }
        }
        return flag
    }

    fun validateLoginCredentials(email: String, password: String) : Boolean {
        users = Database.getUsers()
        println("Users: login() : $users")
        var flag = false
        for((_,user) in users) {
            if (email == user.userEmail && password == user.password) {
                flag = true
                break
            }
        }
        return flag
    }
}
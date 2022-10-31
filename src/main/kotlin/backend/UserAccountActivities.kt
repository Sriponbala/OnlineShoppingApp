package backend

import data.Database
import data.User
import utils.Helper

class UserAccountActivities {

    private val users = Database.getUsers()

    fun createAccount(userName: String, userMobile: String, userEmail: String, password: String) {
        val user = User(Helper.generateUserId(), userName, userMobile,userEmail,password)
        Database.addUser(user)
    }

}
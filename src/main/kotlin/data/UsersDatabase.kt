package data

import utils.Utility
import javax.rmi.CORBA.Util

class UsersDatabase private constructor() {

    private val users: MutableMap<String, User> = mutableMapOf()
    private val usersPassword: MutableMap<String, UserPassword> = mutableMapOf()
    companion object {
        private val INSTANCE by lazy { UsersDatabase() }
        fun getInstance(userName: String, password: String): UsersDatabase? {
            return if(userName == "root" && password == "tiger") {
                INSTANCE
            } else {
                null
            }
        }
    }

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

    fun getUser(mobile: String): User {
        var userData: User = User("","","","")
        for((_, user) in users) {
            if(mobile == user.userMobile) {
                userData = user
            }
        }
        return userData
    }

//    fun getUserPassword(mobile: String): String {
//        getUserId(mobile)
//    }
    fun getUsers() = users
    fun getUsersPassword() = usersPassword

}
package data

object Database {

    private val users: MutableMap<String, User> = mutableMapOf()

    fun addUser(user: User) {
        users[user.userId] = user
    }

    fun getUsers() = users

}
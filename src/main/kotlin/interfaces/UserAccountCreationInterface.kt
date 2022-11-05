package interfaces

interface UserAccountCreationInterface {
    fun createUserAccount(userId: String, userName: String, userMobile: String, userEmail: String, password: String)
    fun getUser(mobile: String)
}
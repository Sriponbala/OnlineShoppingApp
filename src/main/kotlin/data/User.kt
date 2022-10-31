package data

data class User(val userId: String, var userName: String, var userMobile: String, var userEmail: String, var password: String) {
    val addresses =  mutableListOf<Address>()
}



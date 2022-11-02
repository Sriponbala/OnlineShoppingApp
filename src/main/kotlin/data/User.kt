package data

data class User(val userId: String, var userName: String, val userMobile: String, var userEmail: String) { // var password: String
    val addresses =  mutableMapOf<String, Address>() // addressId, address
}



package data

import enums.ProductStatus

data class User(val userId: String, var userName: String, val userMobile: String, var userEmail: String, val userPassword: UserPassword?) {
    val addresses =  mutableMapOf<String, Address>() // addressId, address
}

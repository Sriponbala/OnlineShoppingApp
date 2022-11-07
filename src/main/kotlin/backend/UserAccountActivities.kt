package backend


import data.AccountInfo
import data.Address
import data.User
import utils.UserData
import utils.Utility

class UserAccountActivities {

    private lateinit var user: User
    private lateinit var addressesList: MutableMap<String, Address>
    private val userData = UserData()
    private val utility = Utility()

    fun getUser(userId: String) {
        if(utility.checkIfUserExists(userId)) {
            this.user = userData.retrieveUser(userId)
        }
    }

    fun getUserId(mobile: String): String {
        return userData.retrieveUserId(mobile)
    }

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String {
        return userData.createAndGetUserId(userName, userMobile, userEmail, password)
    }

    fun createAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String) {
        if(utility.checkIfUserExists(userId)) {
            userData.createUserAccountInfo(userId, cartId, wishListId, ordersHistoryId)
        }
    }

    fun getAccountInfo(userId: String): AccountInfo? {
        val accountInfo: AccountInfo? = if(utility.checkIfUserExists(userId)) {
            if(utility.checkIfUserAccountInfoExists(userId)) {
                userData.retrieveAccountInfo(userId)
            } else null
        } else null
        return accountInfo
    }

    fun getUserDetails(): MutableMap<String, String> {
        return if(::user.isInitialized) {
            mutableMapOf("name" to user.userName, "mobile" to user.userMobile, "email" to user.userEmail)
        } else mutableMapOf()
    }

    fun getUserAddresses(): MutableMap<String, Address> {
        if(::user.isInitialized) {
            addressesList = user.addresses
        }
        return addressesList
    }

    fun getShippingAddress(addressId: String): String {
        return if(addressesList.containsKey(addressId)) {
            addressesList[addressId].toString()
        } else ""
    }

    fun updateName(name: String) {
      user.userName = name
    }

    fun updateEmail(email: String) {
     user.userEmail = email
    }

    fun addNewAddress(doorNo: String, flatName: String, street: String, area: String, city: String, state: String, pincode: String) {
        user.addresses[generateAddressId()] = Address(doorNo, flatName, street, area, city, state, pincode)
    }

    fun updateAddress(addressId: String, field: String, value: String) {
        if(user.addresses.containsKey(addressId)) {
                when(field) {
                    "doorNo" -> {
                        user.addresses[addressId]?.doorNo = value
                    }
                    "flatName" -> {
                        user.addresses[addressId]?.flatName = value
                    }
                    "street" -> {
                        user.addresses[addressId]?.street = value
                    }
                    "area" -> {
                        user.addresses[addressId]?.doorNo = value
                    }
                    "city" -> {
                        user.addresses[addressId]?.doorNo = value
                    }
                    "state" -> {
                        user.addresses[addressId]?.state = value
                    }
                    "pincode" -> {
                        user.addresses[addressId]?.pincode = value
                    }
                }
        }
    }

    fun deleteAddress(addressId: String) {
        if(user.addresses.containsKey(addressId)) {
            user.addresses.remove(addressId)
        }
    }

    private var addressId = 1
    private fun generateAddressId(): String {
        return "Address${addressId++}"
    }
}
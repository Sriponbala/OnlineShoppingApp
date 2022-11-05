package backend

import data.Address
import data.Order
import data.OrdersHistoryRecord
import data.User
import interfaces.UserAccountCreationInterface
import interfaces.UserIdInterface
import utils.Helper
import utils.OrdersData
import utils.UserData

class UserAccountActivities: UserIdInterface {

    private lateinit var user: User
    private lateinit var addressesList: MutableMap<String, Address>
    private val userData = UserData()
    fun createUserAccount(userName: String, userMobile: String, userEmail: String, password: String) {
        userData.createUserAccount(userName, userMobile, userEmail, password)
    }

    fun getUser(mobile: String) {
        this.user = userData.retrieveUser(mobile)!!
    }

    override fun getUserId(): String {
        return user.userId
    }

    fun getUserDetails(): MutableMap<String, String> {
       return mutableMapOf("name" to user.userName, "mobile" to user.userMobile, "email" to user.userEmail)
    }

    fun getUserAddresses(): MutableMap<String, Address> {
        addressesList = user.addresses
        return addressesList
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
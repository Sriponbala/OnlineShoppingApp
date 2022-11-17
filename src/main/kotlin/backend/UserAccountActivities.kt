package backend


import data.AccountInfo
import data.Address
import data.User
import interfaces.UserDao
import interfaces.UtilityDao
import utils.UserData

class UserAccountActivities(private val utility: UtilityDao) {

    private lateinit var user: User
    private var addressesList: MutableMap<String, Address> = mutableMapOf()
    private val userDao: UserDao = UserData()
    private var addressId = 1

    fun getUser(userId: String) {

        if(utility.checkIfUserExists(userId)) {
            this.user = userDao.retrieveUser(userId)
        }
    }

    fun getUserId(mobile: String): String {

        return userDao.retrieveUserId(mobile)
    }

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String {

        return userDao.createAndGetUserId(userName, userMobile, userEmail, password)
    }

    fun createAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String): Boolean {

        return if(utility.checkIfUserExists(userId)) {
            userDao.createUserAccountInfo(userId, cartId, wishListId, ordersHistoryId)
            true
        } else false
    }

    fun getAccountInfo(userId: String): AccountInfo? {

        val accountInfo: AccountInfo? = if(utility.checkIfUserExists(userId)) {
            if(utility.checkIfUserAccountInfoExists(userId)) {
                userDao.retrieveAccountInfo(userId)
            } else null
        } else null
        return accountInfo
    }

    fun getUserDetails(): MutableMap<String, String> {

        return mutableMapOf("name" to user.userName, "mobile" to user.userMobile, "email" to user.userEmail)
    }

    fun getUserAddresses(): MutableMap<String, Address> {

        addressesList = userDao.getUserAddresses(user.userId)
        return addressesList
    }

    fun getShippingAddress(addressId: String): String {

        val selectedAddress: Address
        return if(addressesList.containsKey(addressId)) {
            selectedAddress = addressesList[addressId]!!
            "${selectedAddress.doorNo}, ${selectedAddress.flatName}, ${selectedAddress.street}, ${selectedAddress.area}, ${selectedAddress.city}, ${selectedAddress.state}, ${selectedAddress.pincode}"
        } else ""
    }

    fun updateName(name: String): Boolean {

        return if(utility.checkIfUserExists(user.userId)) {
            userDao.updateName(user.userId, name)
            true
        } else false
    }

    fun updateEmail(email: String): Boolean {

        return if(utility.checkIfUserExists(user.userId)) {
            userDao.updateEmail(user.userId, email)
            true
        } else false
    }

    fun addNewAddress(doorNo: String, flatName: String, street: String, area: String, city: String, state: String, pincode: String): Boolean {

        return if(utility.checkIfUserExists(user.userId)) {
            val addressId = generateAddressId()
            userDao.addNewAddress(user.userId, addressId, Address(doorNo, flatName, street, area, city, state, pincode))
            true
        } else false
    }

    fun updateAddress(addressId: String, field: String, value: String) {

        if(utility.checkIfUserExists(user.userId)) {
            if(utility.checkIfAddressExists(user.userId, addressId)) {
                when(field) {
                    "doorNo" -> {
                        userDao.updateAddress(user.userId, addressId,"doorNo", value)
                    }
                    "flatName" -> {
                        userDao.updateAddress(user.userId, addressId,"flatName", value)
                    }
                    "street" -> {
                        userDao.updateAddress(user.userId, addressId,"street", value)
                    }
                    "area" -> {
                        userDao.updateAddress(user.userId, addressId,"area", value)
                    }
                    "city" -> {
                        userDao.updateAddress(user.userId, addressId,"city", value)
                    }
                    "state" -> {
                        userDao.updateAddress(user.userId, addressId,"state", value)
                    }
                    "pincode" -> {
                        userDao.updateAddress(user.userId, addressId,"pincode", value)
                    }
                }
            }
        }
    }

    fun deleteAddress(addressId: String): Boolean {

        return if(utility.checkIfUserExists(user.userId)) {
            if(utility.checkIfAddressExists(user.userId, addressId)) {
                userDao.deleteAddress(user.userId, addressId)
                true
            } else false
        } else false
    }


    private fun generateAddressId(): String {

        return "Address${addressId++}"
    }
}
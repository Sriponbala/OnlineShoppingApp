package backend

import data.AccountInfo
import data.Address
import data.User
import data.UserAddress
import enums.AddressFields
import interfaces.UserDao
import interfaces.UtilityDao

class UserAccountActivities(private val utility: UtilityDao, private val userDao: UserDao) {

    private lateinit var user: User
    private lateinit var addressesList: List<Address>

    fun getUser(userId: String) {
        if(utility.checkIfUserExists(userId)) {
            this.user = userDao.retrieveUser(userId)
        }
    }

    fun retrieveUser(): User {
        return user
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

    fun getUserAddresses(): List<Address> {
        addressesList = userDao.getUserAddresses(user.userId)
        return addressesList
    }

    fun getShippingAddress(addressId: String): Address {
        lateinit var selectedAddress: Address
        for(address in addressesList) {
            if(addressId == address.addressId) {
                selectedAddress = address
                break
            }
        }
        return selectedAddress
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
            val address = Address(doorNo, flatName, street, area, city, state, pincode)
            userDao.addAddress(address)
            val userAddress = UserAddress(user.userId, address.addressId)
            userDao.addNewAddress(userAddress)
            true
        } else false
    }

    fun updateAddress(addressId: String, field: AddressFields, value: String) {
        if(utility.checkIfUserExists(user.userId)) {
            if(utility.checkIfAddressExists(addressId)) {
                when(field) {
                    AddressFields.DOORNUMBER -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.DOORNUMBER, value)
                    }
                    AddressFields.FLATNAME -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.FLATNAME, value)
                    }
                    AddressFields.STREET -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.STREET, value)
                    }
                    AddressFields.AREA -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.AREA, value)
                    }
                    AddressFields.CITY -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.CITY, value)
                    }
                    AddressFields.STATE -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.STATE, value)
                    }
                    AddressFields.PINCODE -> {
                        userDao.updateAddress(user.userId, addressId, AddressFields.PINCODE, value)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteAddress(addressId: String): Boolean {
        return if(utility.checkIfUserExists(user.userId)) {
            if(utility.checkIfAddressExists(addressId)) {
                userDao.deleteAddress(addressId)
                true
            } else false
        } else false
    }

}
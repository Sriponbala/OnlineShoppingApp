package interfaces

import data.AccountInfo
import data.Address
import data.User

interface UserDao {

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String)

    fun retrieveAccountInfo(userId: String): AccountInfo

    fun retrieveUserId(mobile: String): String

    fun retrieveUser(userId: String): User

    fun addNewAddress(userId: String, addressId: String, address: Address)

    fun updateName(userId: String, name: String)

    fun updateEmail(userId: String, email: String)

    fun getUserAddresses(userId: String): MutableMap<String, Address>

    fun deleteAddress(userId: String, addressId: String)

    fun updateAddress(userId: String, addressId: String, field: String, value: String)

}
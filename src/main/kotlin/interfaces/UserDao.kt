package interfaces

import data.AccountInfo
import data.Address
import data.User
import enums.AddressField

interface UserDao {

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String)

    fun retrieveAccountInfo(userId: String): AccountInfo

    fun retrieveUserId(mobile: String): String

    fun retrieveUser(userId: String): User

    fun addAddress(address: Address)

    fun updateName(userId: String, name: String)

    fun updateEmail(userId: String, email: String)

    fun getUserAddresses(userId: String): MutableList<Address>

    fun deleteAddress(addressId: String)

    fun updateAddress(addressId: String, field: AddressField, value: String)

}
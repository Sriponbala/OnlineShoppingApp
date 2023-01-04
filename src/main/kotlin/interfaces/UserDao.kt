package interfaces

import data.AccountInfo
import data.Address
import data.User
import data.UserAddress
import enums.AddressFields

interface UserDao {

    fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String

    fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String)

    fun retrieveAccountInfo(userId: String): AccountInfo

    fun retrieveUserId(mobile: String): String

    fun retrieveUser(userId: String): User

    fun addAddress(address: Address)

    fun addNewAddress(userAddress: UserAddress)

    fun updateName(userId: String, name: String)

    fun updateEmail(userId: String, email: String)

    fun getUserAddresses(userId: String): MutableList<Address>

    fun deleteAddress(addressId: String)

    fun updateAddress(userId: String, addressId: String, field: AddressFields, value: String)

}
package utils

import data.AccountInfo
import data.Address
import data.User
import data.UserPassword
import database.UsersTable
import interfaces.UserDao

class UserData: UserDao {

    override fun createAndGetUserId(userName: String, userMobile: String, userEmail: String, password: String): String {

        val userId = UsersTable.generateUserId()
        val user = User(userId, userName, userMobile,userEmail, UserPassword(userId, password))
        UsersTable.users[user.userId] = user
        return user.userId
    }

    override fun createUserAccountInfo(userId: String, cartId: String, wishListId: String, ordersHistoryId: String) {

        UsersTable.usersAccountInfo[userId] = AccountInfo(cartId, wishListId, ordersHistoryId)
    }

    override fun retrieveAccountInfo(userId: String): AccountInfo {

        return UsersTable.usersAccountInfo[userId]!!
    }

    override fun retrieveUserId(mobile: String): String {

        var id = ""
        for((userId, user) in UsersTable.users) {
            if(mobile == user.userMobile) {
                id = userId
                break
            }
        }
        return id
    }

    override fun retrieveUser(userId: String): User {

        return UsersTable.users[userId]!!.copy(userPassword = null)
    }

    override fun addNewAddress(userId: String, addressId: String, address: Address) {

        UsersTable.users[userId]!!.addresses[addressId] = address
    }

    override fun updateName(userId: String, name: String) {

        UsersTable.users[userId]!!.userName = name
    }

    override fun updateEmail(userId: String, email: String) {

        UsersTable.users[userId]!!.userEmail = email
    }

    override fun getUserAddresses(userId: String): MutableMap<String, Address> {

        return UsersTable.users[userId]!!.addresses
    }

    override fun deleteAddress(userId: String, addressId: String) {

        UsersTable.users[userId]!!.addresses.remove(addressId)
    }

    override fun updateAddress(userId: String, addressId: String, field: String, value: String) {

            when(field) {
                "doorNo" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.doorNo = value
                }
                "flatName" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.flatName = value
                }
                "street" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.street = value
                }
                "area" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.area = value
                }
                "city" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.city = value
                }
                "state" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.state = value
                }
                "pincode" -> {
                    UsersTable.users[userId]!!.addresses[addressId]!!.pincode = value
                }
            }
    }

}
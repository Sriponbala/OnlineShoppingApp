package userInterface

import backend.UserAccountActivities
import data.AccountInfo
import enums.UserAccountDashboard
import enums.UserAccountFields
import interfaces.DashboardServices
import utils.Helper

class UserAccountPage(private val userId: String, private val accountInfo: AccountInfo): DashboardServices {

    private val userAccountActivities = UserAccountActivities()

    private fun displayUserDetails(userDetails: MutableMap<String, String>) {
        println("---------------Your Profile--------------")
        println("""|Name   : ${userDetails["name"]} 
                   |Mobile : ${userDetails["mobile"]} 
                   |Email  : ${userDetails["email"]}""".trimMargin())
    }

    fun openUserAccountPage() {
        userAccountActivities.getUser(userId)
       // accountInfo = userAccountActivities.getAccountInfo(userId)!!
        displayUserDetails(userAccountActivities.getUserDetails())
        val userAccountDashboard = UserAccountDashboard.values()
        while(true) {
            super.showDashboard("Your Account", userAccountDashboard)
            when(super.getUserChoice(userAccountDashboard)) {
                UserAccountDashboard.VIEW_WISHLIST -> {
                    WishListPage(accountInfo.wishListId).openWishListPage()
                }
                UserAccountDashboard.VIEW_ORDERS_HISTORY -> {
                    OrdersPage(accountInfo.ordersHistoryId).displayOrdersHistory()
                }
                UserAccountDashboard.EDIT_ACCOUNT -> {
                    editUserAccountDetails()
                    println("user : ${userAccountActivities.getUserDetails()}")
                }
                UserAccountDashboard.GO_BACK -> break
            }
        }
    }


    private fun editUserAccountDetails() {
        val userAccountFields = UserAccountFields.values()
        while(true) {
            super.showDashboard("Edit User Details", userAccountFields)
            println("Select the field to edit:")
            when(super.getUserChoice(userAccountFields)) {
                UserAccountFields.Name -> {
                    var name: String
                    do{
                        println("Enter name: ")
                        name = readLine()!!
                    } while(Helper.fieldValidation(name))
                    userAccountActivities.updateName(name)
                }
                UserAccountFields.Email -> {
                    var email: String
                    do {
                        println("Enter email: ")
                        email = readLine()!!
                    } while(!Helper.fieldValidation(email) && !Helper.validateEmail(email))
                    userAccountActivities.updateEmail(email)
                }
                UserAccountFields.Addresses -> {
                    val addressPage = AddressPage(userAccountActivities)
                    addressPage.openAddressPage()
                }
                UserAccountFields.Back -> {
                    break
                }
            }
        }
    }

}
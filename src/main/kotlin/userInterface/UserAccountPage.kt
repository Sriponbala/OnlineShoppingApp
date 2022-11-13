package userInterface

import backend.UserAccountActivities
import data.AccountInfo
import enums.UserAccountDashboard
import enums.UserAccountFields
import interfaces.DashboardServices
import utils.Helper

class UserAccountPage(private val userAccountActivities: UserAccountActivities): DashboardServices {

    private lateinit var userId: String
    private lateinit var accountInfo: AccountInfo

    fun initializer(userId: String, accountInfo: AccountInfo) {
        this.userId = userId
        this.accountInfo = accountInfo
    }

    private fun displayUserDetails(userDetails: MutableMap<String, String>) {
        println("---------------YOUR PROFILE--------------")
        println("""|NAME   : ${userDetails["name"]} 
                   |MOBILE : ${userDetails["mobile"]} 
                   |EMAIL  : ${userDetails["email"]}""".trimMargin())
    }

    fun openUserAccountPage(wishListPage: WishListPage, ordersPage: OrdersPage, addressPage: AddressPage, shopPage: ShopPage) {
        userAccountActivities.getUser(userId)
        displayUserDetails(userAccountActivities.getUserDetails())
        val userAccountDashboard = UserAccountDashboard.values()
        while(true) {
            super.showDashboard("Your Account", userAccountDashboard)
            when(super.getUserChoice(userAccountDashboard)) {
                UserAccountDashboard.VIEW_WISHLIST -> {
                    wishListPage.initializer(accountInfo.wishListId, shopPage)
                    wishListPage.openWishListPage()
                }
                UserAccountDashboard.VIEW_ORDERS_HISTORY -> {
                    ordersPage.initializer(accountInfo.ordersHistoryId)
                    ordersPage.displayOrdersHistory()
                }
                UserAccountDashboard.EDIT_ACCOUNT -> {
                    editUserAccountDetails(addressPage)
                    println("user : ${userAccountActivities.getUserDetails()}")
                }
                UserAccountDashboard.GO_BACK -> break
            }
        }
    }


    private fun editUserAccountDetails(addressPage: AddressPage) {
        val userAccountFields = UserAccountFields.values()
        while(true) {
            super.showDashboard("Edit User Details", userAccountFields)
            println("SELECT THE FIELD TO EDIT:")
            when(super.getUserChoice(userAccountFields)) {
                UserAccountFields.Name -> {
                    var name: String
                    do{
                        println("ENTER NAME: ")
                        name = readLine()!!
                    } while(Helper.fieldValidation(name))
                    userAccountActivities.updateName(name)
                }
                UserAccountFields.Email -> {
                    var email: String
                    do {
                        println("ENTER EMAIL: ")
                        email = readLine()!!
                    } while(!Helper.fieldValidation(email) && !Helper.validateEmail(email))
                    userAccountActivities.updateEmail(email)
                }
                UserAccountFields.Addresses -> {
                    addressPage.openAddressPage()
                }
                UserAccountFields.Back -> {
                    break
                }
            }
        }
    }

}
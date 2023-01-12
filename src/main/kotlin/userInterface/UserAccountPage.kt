package userInterface

import backend.UserAccountActivities
import data.AccountInfo
import data.User
import enums.UserAccountDashboard
import enums.UserAccountField
import interfaces.DashboardServices
import utils.Helper

class UserAccountPage(private val userAccountActivities: UserAccountActivities): DashboardServices {

    private lateinit var accountInfo: AccountInfo
    private lateinit var user: User

    fun initializer(accountInfo: AccountInfo) {
        this.accountInfo = accountInfo
    }

    private fun displayUserDetails(user: User) {
        println("---------------YOUR PROFILE--------------")
        println("""|NAME   : ${user.userName} 
                   |MOBILE : ${user.userMobile} 
                   |EMAIL  : ${user.userEmail}""".trimMargin())
    }

    fun openUserAccountPage(wishListPage: WishListPage, ordersPage: OrdersPage, addressPage: AddressPage, shopPage: ShopPage) {
        userAccountActivities.getUser(accountInfo.userId)
        this.user = userAccountActivities.retrieveUser()
        displayUserDetails(this.user)
        val userAccountDashboard = UserAccountDashboard.values()
        while(true) {
            super.showDashboard("YOUR ACCOUNT", userAccountDashboard)
            when(super.getUserChoice(userAccountDashboard)) {

                UserAccountDashboard.VIEW_WISHLIST -> {
                    wishListPage.initializer(accountInfo.wishListId, shopPage)
                    wishListPage.openWishListPage()
                }

                UserAccountDashboard.VIEW_ORDERS_HISTORY -> {
                    ordersPage.displayOrdersHistory(accountInfo.userId)
                }

                UserAccountDashboard.EDIT_ACCOUNT -> {
                    editUserAccountDetails(addressPage)
                }

                UserAccountDashboard.GO_BACK -> break
            }
        }
    }


    private fun editUserAccountDetails(addressPage: AddressPage) {
        val userAccountFields = UserAccountField.values()
        while(true) {
            super.showDashboard("EDIT USER DETAILS", userAccountFields)
            println("SELECT THE FIELD TO EDIT:")
            when(super.getUserChoice(userAccountFields)) {

                UserAccountField.Name -> {
                    var name: String
                    do{
                        println("ENTER NAME: ")
                        name = readLine()!!
                    } while(Helper.fieldValidation(name))
                    if(userAccountActivities.updateName(name)) {
                        println("Updated name successfully!")
                    } else println("Failed to update name!")
                }

                UserAccountField.Email -> {
                    var email: String
                    do {
                        println("ENTER EMAIL: ")
                        email = readLine()!!
                    } while(!Helper.fieldValidation(email) && !Helper.validateEmail(email))
                    if(userAccountActivities.updateEmail(email)) {
                        println("Updated email successfully!")
                    } else println("Failed to update email!")

                }
                UserAccountField.Addresses -> {
                    addressPage.openAddressPage()
                }

                UserAccountField.Back -> {
                    break
                }
            }
        }
    }

}
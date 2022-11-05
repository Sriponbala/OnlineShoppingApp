package userInterface

import backend.UserAccountActivities
import enums.UserAccountDashboard
import enums.UserAccountFields
import interfaces.DashboardServices
import utils.Helper

class UserAccountPage(private val userAccountActivities: UserAccountActivities): DashboardServices {

    fun displayUserDetails(userDetails: MutableMap<String, String>) {
        println("---------------Your Profile--------------")
        println("""|Name   : ${userDetails["name"]} 
                   |Mobile : ${userDetails["mobile"]} 
                   |Email  : ${userDetails["email"]}""".trimMargin())
    }
    override fun showDashboard() {

        while (true) {
            println("------------Your Account-------------")
            for(element in UserAccountDashboard.values()) {
                println("${element.ordinal+1}. $element")
            }
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, UserAccountDashboard.values().size)) {
                    if(doDashboardActivities(UserAccountDashboard.values()[dashBoardOption-1])) {
                        break
                    }
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class UserAccountPage: showDashBoard(): Exception: $exception")
            }
        }
    }
    override fun <E : Enum<E>> doDashboardActivities(enumConstant: Enum<E>): Boolean {

        when(enumConstant) {

            UserAccountDashboard.VIEW_WISHLIST -> {
                WishListPage().openWishListPage(userAccountActivities.getUserId())
                return false
            }
            UserAccountDashboard.VIEW_ORDERS_HISTORY -> {
                OrdersPage().displayOrdersHistory(userAccountActivities.getUserId())
                return false
            }
            UserAccountDashboard.EDIT_ACCOUNT -> {
                editUserAccountDetails()
                println("user : ${userAccountActivities.getUserDetails()}")
                return false
            }
            UserAccountDashboard.GO_BACK -> return true
            else -> {
                println("Invalid dashboard option!")
                return false
            }
        }
    }

    private fun editUserAccountDetails() {

        while(true) {
            println("-------------Edit User Details------------")
            println("Select the field to edit:")
            for(field in UserAccountFields.values()) {
                println("${field.ordinal + 1}. $field")
            }
            try{
                val choice = readLine()!!
                val editOption = choice.toInt()
                when(UserAccountFields.values()[editOption - 1]) {
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
            } catch(exception: Exception) {
                println("Class UserAccountPage: editUserDetails(): Exception: $exception")
            }
        }
    }

}
package userInterface

import backend.UserAccountActivities
import enums.HomePageDashboard
import interfaces.DashboardServices
import utils.Helper

class HomePage {

     fun showDashboard(userAccountActivities: UserAccountActivities) {

        while (true) {
            println("------------Home Page-------------")
            for(element in HomePageDashboard.values()) {
                println("${element.ordinal+1}. $element")
            }
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, HomePageDashboard.values().size)) {
                    if(doDashboardActivities(HomePageDashboard.values()[dashBoardOption-1], userAccountActivities)) {
                        break
                    }
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class HomePage: showDashBoard(): Exception: $exception")
            }
        }
    }

    private fun doDashboardActivities(enumConstant: HomePageDashboard, userAccountActivities: UserAccountActivities): Boolean {

        when(enumConstant) {
            HomePageDashboard.VIEW_PRODUCTS -> {
               // ShopPage().shoppingActivities()
                return false
            }
            HomePageDashboard.VIEW_CART -> {
               // ViewCart().displayCartItems()
                return false
            }
            HomePageDashboard.YOUR_ACCOUNT -> {
                val userAccountPage = UserAccountPage(userAccountActivities)
                userAccountPage.displayUserDetails(userAccountActivities.getUserDetails())
                userAccountPage.showDashboard()
                return false
            }
            HomePageDashboard.SIGN_OUT -> {
                println("Signed out...")
                return true
            }
            else -> {
                println("Invalid dashboard option!")
                return false
            }
        }
    }
}
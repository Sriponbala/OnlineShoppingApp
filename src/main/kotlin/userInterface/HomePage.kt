package userInterface

import enums.HomePageDashboard
import utils.Helper

class HomePage {

     fun showDashboard(userId: String) {

        while (true) {
            println("------------Home Page-------------")
            for(element in HomePageDashboard.values()) {
                println("${element.ordinal+1}. $element")
            }
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, HomePageDashboard.values().size)) {
                    if(doDashboardActivities(HomePageDashboard.values()[dashBoardOption-1], userId)) {
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

    private fun doDashboardActivities(enumConstant: HomePageDashboard, userId: String): Boolean {

        when(enumConstant) {
            HomePageDashboard.VIEW_PRODUCTS -> {
                val shopPage = ShopPage()
                shopPage.setUserIdAndAccountInfo(userId)
                shopPage.openShopPage()
                return false
            }
            HomePageDashboard.VIEW_CART -> {
                val cartPage = CartPage()
                cartPage.setUserIdAndCartId(userId)
                cartPage.openCartPage()
                return false
            }
            HomePageDashboard.YOUR_ACCOUNT -> {
                val userAccountPage = UserAccountPage()
                userAccountPage.openUserAccountPage(userId)
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
package userInterface

import backend.UserAccountActivities
import data.AccountInfo
import enums.HomePageDashboard
import interfaces.DashboardServices

class HomePage(): DashboardServices {

    private lateinit var userId: String
    private var accountInfo: AccountInfo? =  null
    private val userAccountActivities by lazy { UserAccountActivities() }

    constructor(userId: String): this() {
        this.userId = userId
        this.accountInfo = userAccountActivities.getAccountInfo(userId)
    }

    fun openHomePage() {
        val homePageDashboard = HomePageDashboard.values()
        while(true) {
            super.showDashboard("Home Page", homePageDashboard)
            when(super.getUserChoice(homePageDashboard)) {
                HomePageDashboard.VIEW_PRODUCTS -> {
                    if(::userId.isInitialized && accountInfo != null) {
                        val shopPage = ShopPage(userId, accountInfo!!)
                        shopPage.openShopPage()
                    } else {
                        val shopPage = ShopPage()
                        shopPage.openShopPage()
                    }
                }
                HomePageDashboard.VIEW_CART -> {
                    if(::userId.isInitialized && accountInfo != null) {
                        val cartPage = CartPage(userId, accountInfo!!.cartId)
                        cartPage.openCartPage()
                    } else {
                        println("No items found in cart! Login to add items!")
                    }
                }
                HomePageDashboard.YOUR_ACCOUNT -> {
                    if(::userId.isInitialized && accountInfo != null) {
                        val userAccountPage = UserAccountPage(userId, accountInfo!!)
                        userAccountPage.openUserAccountPage()
                    } else {
                        println("Login to your account!")
                    }
                }
                HomePageDashboard.SIGN_OUT -> {
                    if(::userId.isInitialized && accountInfo != null) {
                        println("Signed out...")
                    } else {
                        println("Login to your account!")
                    }
                    break
                }
            }
        }
    }

}
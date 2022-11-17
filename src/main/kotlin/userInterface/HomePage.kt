package userInterface

import backend.CartActivities
import backend.WishListsActivities
import data.AccountInfo
import enums.HomePageDashboard
import interfaces.DashboardServices

class HomePage(private val cartActivities: CartActivities, private val wishListsActivities: WishListsActivities): DashboardServices {

    private lateinit var userId: String
    private lateinit var accountInfo: AccountInfo
    private var isLoggedIn: Boolean = false


    fun initializeUserIdAndAccountInfo(userId: String, accountInfo: AccountInfo) {

        this.userId = userId
        this.accountInfo = accountInfo
        this.isLoggedIn = true
    }

    fun openHomePage(
        shopPage: ShopPage,
        cartPage: CartPage,
        userAccountPage: UserAccountPage,
        addressPage: AddressPage,
        ordersPage: OrdersPage,
        wishListPage: WishListPage,
        checkOutPage: CheckOutPage,
        paymentPage: PaymentPage
    ) {

        val homePageDashboard = HomePageDashboard.values()
        while(true) {
            super.showDashboard("HOME PAGE", homePageDashboard)
            when(super.getUserChoice(homePageDashboard)) {
                HomePageDashboard.VIEW_PRODUCTS -> {
                    if(isLoggedIn) {
                        shopPage.initializer(userId, accountInfo, wishListsActivities, cartActivities, checkOutPage, addressPage, paymentPage)
                        shopPage.openShopPage()
                    } else {
                        shopPage.openShopPage()
                    }
                }
                HomePageDashboard.VIEW_CART -> {
                    if(isLoggedIn) {
                        cartPage.initializer(accountInfo.cartId)
                        cartPage.openCartPage(addressPage, paymentPage, checkOutPage, accountInfo)
                    } else {
                        println("No items found in cart! Login to add items!")
                    }
                }
                HomePageDashboard.YOUR_ACCOUNT -> {
                    if(isLoggedIn) {
                        userAccountPage.initializer(userId, accountInfo)
                        userAccountPage.openUserAccountPage(wishListPage, ordersPage, addressPage, shopPage)
                    } else {
                        println("Login to your account!")
                    }
                }
                HomePageDashboard.SIGN_OUT -> {
                    if(isLoggedIn) {
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
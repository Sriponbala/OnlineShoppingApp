package userInterface

import backend.CartActivities
import backend.WishListsActivities
import data.AccountInfo
import enums.HomePageDashboard
import interfaces.DashboardServices

class HomePage(private val cartActivities: CartActivities, private val wishListsActivities: WishListsActivities): DashboardServices {

    private lateinit var accountInfo: AccountInfo
    private var isLoggedIn: Boolean = false

    fun initializer(accountInfo: AccountInfo) {
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
        val homePageDashboard: Array<HomePageDashboard> = if(isLoggedIn) {
            arrayOf(HomePageDashboard.VIEW_PRODUCTS, HomePageDashboard.VIEW_CART, HomePageDashboard.YOUR_ACCOUNT, HomePageDashboard.SIGN_OUT)
        } else {
            HomePageDashboard.values().copyOfRange(0,4)
        }
        while(true) {
            super.showDashboard("HOME PAGE", homePageDashboard)
            when(super.getUserChoice(homePageDashboard)) {
                HomePageDashboard.VIEW_PRODUCTS -> {
                    if(isLoggedIn) {
                        shopPage.initializer(accountInfo, wishListsActivities, cartActivities, checkOutPage, addressPage, paymentPage)
                        shopPage.openShopPage()
                    } else {
                        shopPage.initializer(false)
                        shopPage.openShopPage()
                    }
                }
                HomePageDashboard.VIEW_CART -> {
                    if(isLoggedIn) {
                        cartPage.initializer(accountInfo.cartId)
                        cartPage.openCartPage(addressPage, paymentPage, checkOutPage, accountInfo)
                    } else {
                        println("Login to add items in cart!")
                    }
                }
                HomePageDashboard.YOUR_ACCOUNT -> {
                    if(isLoggedIn) {
                        userAccountPage.initializer(accountInfo)
                        userAccountPage.openUserAccountPage(wishListPage, ordersPage, addressPage, shopPage)
                    } else {
                        println("Login to your account!")
                    }
                }
                HomePageDashboard.SIGN_OUT -> {
                    isLoggedIn = false
                    println("Signed out...")
                    break
                }
                HomePageDashboard.BACK -> break
            }
        }
    }

}
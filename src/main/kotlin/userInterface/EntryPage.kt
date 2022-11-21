package userInterface

import backend.CartActivities
import backend.OrdersHistoryActivities
import backend.UserAccountActivities
import backend.WishListsActivities
import enums.Entry
import interfaces.DashboardServices
import interfaces.UtilityDao

class EntryPage: DashboardServices {

    fun openEntryPage(
        signUpPage: SignUpPage,
        signInPage: SignInPage,
        homePage: HomePage,
        userAccountPage: UserAccountPage,
        shopPage: ShopPage,
        cartPage: CartPage,
        wishListPage: WishListPage,
        checkOutPage: CheckOutPage,
        addressPage: AddressPage,
        ordersPage: OrdersPage,
        paymentPage: PaymentPage,
        userAccountActivities: UserAccountActivities,
        wishListsActivities: WishListsActivities,
        cartActivities: CartActivities,
        ordersHistoryActivities: OrdersHistoryActivities,
        utility: UtilityDao
    ) {
        println("-----ONLINE SHOPPING APPLICATION-----\n")
        val entry = Entry.values()
        while(true) {
            super.showDashboard("ENTRY PAGE", entry)
            when(super.getUserChoice(entry)) {
                Entry.VIEW_APP -> {
                    homePage.openHomePage(shopPage, cartPage, userAccountPage, addressPage, ordersPage, wishListPage, checkOutPage, paymentPage)
                }
                Entry.SIGN_UP -> {
                    signUpPage.signUp(homePage, userAccountActivities, wishListsActivities, cartActivities, ordersHistoryActivities, utility, shopPage, cartPage, userAccountPage, addressPage, ordersPage, wishListPage, checkOutPage, paymentPage)
                }
                Entry.SIGN_IN -> {
                    signInPage.signIn(homePage, userAccountActivities, utility, shopPage, cartPage, userAccountPage, addressPage, ordersPage, wishListPage, checkOutPage, paymentPage)
                }
                Entry.EXIT -> {
                    println("Thank You! Visit again!")
                    break
                }
            }
        }
    }

}
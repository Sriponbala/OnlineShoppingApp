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













//    private fun <E: Enum<E>> showDashboard(title: String, enumArray: Array<E>) {
//        println("-------------${title.uppercase()}-------------")
//        for(element in enumArray) {
//            println("${element.ordinal+1}. $element")
//        }
//    }
//
//    private fun <E: Enum<E>> getUserChoice(enumArray: Array<E>): E {
//        while (true) {
//            try {
//                println("Enter your choice: ")
//                val option = readLine()!!
//                val dashBoardOption = option.toInt()
//                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
//                    return enumArray[dashBoardOption-1]
//                } else {
//                    println("Enter valid option!")
//                }
//            } catch (exception: Exception) {
//                println("Class CartPage: getUserChoice(): Exception: $exception")
//            }
//        }
//    }

//    override fun showDashboard() {
//
//        while(true) {
//            println("------Online Shopping Application------")
//            for(entry in Entry.values()) {
//                println("${entry.ordinal + 1}.$entry")
//            }
//            print("Enter your choice: ")
//            try {
//                val option = readLine()!! // NumberFormatException
//                val entryOption = option.toInt()
//                if(Helper.checkValidRecord(entryOption, Entry.values().size)) {
//                    val entry: Entry = Entry.values()[entryOption-1]  // ArrayIndexOutOfBoundsException
//                    if(!doDashboardActivities(entry)) {
//                        break
//                    }
//                } else {
//                    println("\nEnter valid option!")
//                }
//            } catch (exception: Exception) {
//                println("Class EntryPage: showEntryPage(): Exception: $exception")
//            }
//        }
//    }
//
//    override fun <E : Enum<E>> doDashboardActivities(enumConstant: Enum<E>): Boolean {
//
//        when(enumConstant) {
//            Entry.SIGN_UP -> {
//                SignUpPage().signUp()
//                return true
//            }
//            Entry.SIGN_IN -> {
//                SignInPage().signIn()
//                return true
//            }
//            Entry.EXIT -> {
//                println("Thank You! Visit again!")
//                return false
//            }
//            else -> {
//                println("Invalid option!") // important to add else i.e. if enum changes then will throw error
//                return true
//            }
//        }
//    }
}
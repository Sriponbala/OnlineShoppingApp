import backend.*
import interfaces.*
import userInterface.*
import utils.*

fun main() {

    val utility: UtilityDao = Utility()
    val cartDao: CartDao = CartData()
    val ordersDao: OrdersDao = OrdersData()
    val productsDao: ProductsDao = ProductsData()
    val userDao: UserDao = UserData()
    val wishListsDao: WishListDao = WishListsData()
    val productActivities = ProductActivities(utility, productsDao)
    productActivities.addProductDetails()
    val userAccountActivities = UserAccountActivities(utility, userDao)
    val cartActivities = CartActivities(utility, cartDao, productsDao, productActivities)
    val wishListsActivities = WishListsActivities(utility, wishListsDao)
    val ordersHistoryActivities = OrdersHistoryActivities(productActivities, ordersDao)
    val checkOutActivities = CheckOutActivities(cartActivities, productActivities, ordersHistoryActivities)
    val entryPage = EntryPage()
    val addressPage = AddressPage(userAccountActivities)
    val cartPage = CartPage(cartActivities)
    val checkOutPage = CheckOutPage(checkOutActivities)
    val homePage = HomePage(cartActivities, wishListsActivities)
    val ordersPage = OrdersPage(ordersHistoryActivities)
    val paymentPage = PaymentPage()
    val shopPage = ShopPage(productActivities)
    val signUpPage = SignUpPage()
    val signInPage = SignInPage()
    val userAccountPage = UserAccountPage(userAccountActivities)
    val wishListPage = WishListPage(wishListsActivities)

    entryPage.openEntryPage(signUpPage, signInPage, homePage, userAccountPage, shopPage, cartPage, wishListPage, checkOutPage, addressPage, ordersPage, paymentPage, userAccountActivities, wishListsActivities, cartActivities, utility)
 
}


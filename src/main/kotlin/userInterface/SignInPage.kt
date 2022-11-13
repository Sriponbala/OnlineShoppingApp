package userInterface

import backend.UserAccountActivities
import data.AccountInfo
import interfaces.OnboardingServices
import interfaces.UtilityDao
import utils.Helper

class SignInPage: OnboardingServices {

    private var mobile: String = ""
    private var password: String = ""
    private lateinit var userId: String
    private var accountInfo: AccountInfo? = null

    fun signIn(
        homePage: HomePage,
        userAccountActivities: UserAccountActivities,
        utility: UtilityDao,
        shopPage: ShopPage,
        cartPage: CartPage,
        userAccountPage: UserAccountPage,
        addressPage: AddressPage,
        ordersPage: OrdersPage,
        wishListPage: WishListPage,
        checkOutPage: CheckOutPage,
        paymentPage: PaymentPage
    ) {
        println("----------SIGNIN PAGE----------")
        try {
            getUserInputs()
            while (true) {
                    if (Helper.confirm()) {
                        if (verifyAccount(utility)) {
                            userId = userAccountActivities.getUserId(mobile)
                            userAccountActivities.getUser(userId)
                            accountInfo = userAccountActivities.getAccountInfo(userId)
                            if(accountInfo != null) {
                                println("SignIn Successful!")
                                homePage.initializeUserIdAndAccountInfo(userId, accountInfo!!)
                                homePage.openHomePage(
                                    shopPage,
                                    cartPage,
                                    userAccountPage,
                                    addressPage,
                                    ordersPage,
                                    wishListPage,
                                    checkOutPage,
                                    paymentPage
                                )
                            } else {
                                println("Invalid userId!")
                            }
                            //HomePage(userId).openHomePage()
                        } else {
                            println("SignIn failed!")
                        }
                        break
                    } else {
                        break
                    }
            }
        } catch (exception: Exception) {
            println("Class SignInPage: signIn(): Exception: $exception")
        }
    }

    override fun getUserInputs() {

        do {
            println("ENTER MOBILE NUMBER: ")
            mobile = readLine()!!
        } while(Helper.fieldValidation(mobile) || !Helper.validateMobileNumber(mobile))

        do{
            println("ENTER PASSWORD: \n" +
                    "[Password can contain any of the following : a-zA-Z0-9!#@\$%^&*_+`~]\n" +
                    "[It should contain at least 4 to 8 characters]")
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))
    }

    private fun verifyAccount(utility: UtilityDao): Boolean {
        return utility.validateLoginCredentials(mobile, password)
    }

}
package userInterface

import backend.CartActivities
import backend.OrdersHistoryActivities
import backend.UserAccountActivities
import backend.WishListsActivities
import data.AccountInfo
import interfaces.OnboardingServices
import interfaces.UtilityDao
import utils.Helper
import java.lang.Exception

class SignUpPage: OnboardingServices {

    private var name: String = ""
    private var mobile: String = ""
    private var email: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""
    private lateinit var cartId: String
    private lateinit var wishListId: String
    private lateinit var ordersHistoryId: String
    private lateinit var userId: String
    private var accountInfo: AccountInfo? = null

    fun signUp(
        homePage: HomePage,
        userAccountActivities: UserAccountActivities,
        wishListsActivities: WishListsActivities,
        cartActivities: CartActivities,
        ordersHistoryActivities: OrdersHistoryActivities,
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
        println("--------------SIGNUP PAGE--------------")
        try {
            getUserInputs()
            while(true) {
                if (Helper.confirm()) {
                        if(verifyAccount(utility)) {
                            val otp = Helper.generateOTP()
                            println("OTP : $otp")
                            while(true) {
                                println("ENTER THE OTP: ")
                                val currentOtp = readLine()!!
                                if(Helper.verifyOtp(currentOtp, otp)) {
                                    userId = userAccountActivities.createAndGetUserId(name, mobile, email, password)
                                    userAccountActivities.getUser(userId)
                                    wishListId = wishListsActivities.createAndGetWishListId()
                                    cartId = cartActivities.createAndGetCartId()
                                    ordersHistoryId = ordersHistoryActivities.createAndGetOrdersHistoryId()
                                    if(userAccountActivities.createAccountInfo(userId, cartId, wishListId, ordersHistoryId)) {
                                        accountInfo = userAccountActivities.getAccountInfo(userId)
                                        if(accountInfo != null) {
                                            println("SignUp Successful!")
                                            homePage.initializer(userId, accountInfo!!)
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
                                            println("Account Info not found!")
                                        }
                                    } else {
                                        println("Invalid userId!")
                                    }
                                    break
                                } else {
                                    println("Incorrect OTP! Try again!")
                                }
                            }
                        } else {
                            println("User already exists!")
                        }
                        break
                    } else {
                        break
                    }
            }
        } catch (exception: Exception) {
            println("Class SignUpPage: signUp(): Exception: $exception")
        }
    }

    override fun getUserInputs() {
        do{
            println("ENTER NAME: ")
            name = readLine()!!
        } while(Helper.fieldValidation(name))

        do{
            println("""ENTER MOBILE NUMBER:
                |[Should contain 10 digits] 
            """.trimMargin())
            mobile = readLine()!!
        }while(Helper.fieldValidation(mobile) || !Helper.validateMobileNumber(mobile))

        do {
            println("""ENTER EMAIL:
                |[Format: localpart@example.com] 
            """.trimMargin())
            email = readLine()!!
        } while(!Helper.fieldValidation(email) && !Helper.validateEmail(email))

        do{
            println("""ENTER PASSWORD:
                |[Password can contain any of the following : a-zA-Z0-9!#@${'$'}%^&*_+`~]
                |[It should contain at least 4 to 8 characters]""".trimMargin())
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))

        do{
            println("ENTER CONFIRM PASSWORD: ")
            confirmPassword = readLine()!!
        } while(Helper.fieldValidation(confirmPassword) || !Helper.confirmPassword(confirmPassword,password))
    }

    private fun verifyAccount(utility: UtilityDao): Boolean {
        return utility.checkUniqueUser(mobile)
    }
}
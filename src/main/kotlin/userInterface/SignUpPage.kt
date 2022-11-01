package userInterface

import backend.UserAccountActivities
import interfaces.OnboardingServices
import utils.Helper
import utils.UserDAO
import utils.Utility
import java.lang.Exception

class SignUpPage: OnboardingServices {

    private lateinit var id: String
    private var name: String = ""
    private var mobile: String = ""
    private var email: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""

    fun signUp() {
        println("----------SignUp Page----------")
        try {
            getUserInputs()
            while (true) {
                if (Helper.confirm()) {
                        if(verifyAccount()) {
                            val otp = Helper.generateOTP()
                            println("OTP : $otp")
                            while(true) {
                                println("Enter the OTP: ")
                                val currentOtp = readLine()!!
                                if(Helper.verifyOtp(currentOtp, otp)) {
                                    id = Helper.generateUserId()
                                    val userDAO = UserDAO()
                                    userDAO.createAccount(id, name, mobile, email, password)
                                    val userAccountActivities = UserAccountActivities()
                                    userAccountActivities.getUserId(mobile)
                                    userAccountActivities.getUser(mobile)
                                    println("SignUp Successful!")
                                    HomePage().showDashboard(userAccountActivities)
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
            println("Enter name: ")
            name = readLine()!!
        } while(Helper.fieldValidation(name))

        do{
            println("Enter mobile number: ")
            mobile = readLine()!!
        }while(Helper.fieldValidation(mobile) || !Helper.validateMobileNumber(mobile))

        do {
            println("Enter email: ")
            email = readLine()!!
        } while(!Helper.fieldValidation(email) && !Helper.validateEmail(email))

        do{
            println("Enter password: " +
                    "[Password can contain any of the following : a-zA-Z0-9!#@\$%^&*_+`~]" +
                    "[It should contain atleast 4 to 8 characters]")
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))

        do{
            println("Enter confirm password: ")
            confirmPassword = readLine()!!
        } while(Helper.fieldValidation(confirmPassword) || !Helper.confirmPassword(confirmPassword,password))
    }

    override fun verifyAccount(): Boolean {
        return Utility().checkUniqueUser(mobile)
    }

}
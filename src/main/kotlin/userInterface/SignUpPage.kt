package userInterface

import backend.UserAccountActivities
import interfaces.OnboardingServices
import utils.Helper
import utils.Utility
import java.lang.Exception

class SignUpPage: OnboardingServices {

    private var name: String = ""
    private var mobile: String = ""
    private var email: String = ""
    private var password: String = ""
    private var rePassword: String = ""

    fun signUp() {
        println("----------SignUp Page----------")
        try {
            getUserInputs()
            while (true) {
                if (Helper.confirm()) {
                        if(verifyAccount()) {
                            val userAccountActivities = UserAccountActivities()
                            userAccountActivities.createAccount(name, mobile, email, password)
                            println("Successful SignUp!")
                            val homePage = HomePage()
                          //  homePage.showDashBoard()
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
        } while(Helper.fieldValidation(email) || !Helper.validateEmail(email))

        do{
            println("Enter password: " +
                    "[Password can contain any of the following : a-zA-Z0-9!#@\$%^&*_+`~]" +
                    "[It should contain atleast 4 to 8 characters]")
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))

        do{
            println("Enter re password: ")
            rePassword = readLine()!!
        } while(Helper.fieldValidation(rePassword) || Helper.validatePassword(password,rePassword))
    }

    override fun verifyAccount(): Boolean {
        return Utility.checkUniqueUser(email)
    }



}
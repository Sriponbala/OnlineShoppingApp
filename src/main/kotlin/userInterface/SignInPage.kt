package userInterface

import backend.UserAccountActivities
import interfaces.OnboardingServices
import utils.Helper
import utils.Utility

class SignInPage: OnboardingServices {

    private var mobile: String = ""
    private var password: String = ""

    fun signIn() {
        println("----------SignIn Page----------")
        try {
            getUserInputs()
            while (true) {
                    if (Helper.confirm()) {
                        if (verifyAccount()) {
                            println("SignIn Successful!")
                            val userAccountActivities = UserAccountActivities()
                            userAccountActivities.getUser(mobile)
                            userAccountActivities.getUserId()
                            HomePage().showDashboard(userAccountActivities)
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
            println("Enter mobile number: ")
            mobile = readLine()!!
        } while(Helper.fieldValidation(mobile) || !Helper.validateMobileNumber(mobile))

        do{
            println("Enter password: \n" +
                    "[Password can contain any of the following : a-zA-Z0-9!#@\$%^&*_+`~]\n" +
                    "[It should contain at least 4 to 8 characters]")
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))
    }

    override fun verifyAccount(): Boolean {
        return Utility().validateLoginCredentials(mobile, password)
    }

}
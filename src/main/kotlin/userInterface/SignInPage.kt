package userInterface

import interfaces.OnboardingServices
import utils.Helper
import utils.Utility

class SignInPage: OnboardingServices {

    private var email: String = ""
    private var password: String = ""

    fun signIn() {
        println("----------SignIn Page----------")
        try {
            getUserInputs()
            while (true) {
                    if (Helper.confirm()) {
                        if (verifyAccount()) {
                            println("Successful SignIn!")
                            val homePage = HomePage()
                            //  homePage.showDashBoard()
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
            println("Enter email: ")
            email = readLine()!!
        } while(Helper.fieldValidation(email) || !Helper.validateEmail(email))

        do{
            println("Enter password: \n" +
                    "[Password can contain any of the following : a-zA-Z0-9!#@\$%^&*_+`~]\n" +
                    "[It should contain atleast 4 to 8 characters]")
            password = readLine()!!
        } while(Helper.fieldValidation(password) || !Helper.validatePasswordPattern(password))
    }

    override fun verifyAccount(): Boolean {
        return Utility.validateLoginCredentials(email, password)
    }

}
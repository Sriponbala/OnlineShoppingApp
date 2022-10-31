package utils

import enums.Confirmation
import java.util.regex.Pattern
import kotlin.random.Random

object Helper {

    private var id = 0
    private var pid = 0

    fun generateUserId(): String {
        return "USER${id++}"
    }

    fun generateProductId(): String {
        return "Product${pid++}"
    }

    fun confirm(): Boolean {
        while(true) {
            println("Confirm: ")
            for (option in Confirmation.values()) {
                println("${option.ordinal + 1}. $option")
            }
            try {
                val choice = readLine()!!
                val confirmationChoice = choice.toInt() // chance of exception
                if(checkValidRecord(confirmationChoice, Confirmation.values().size)) {
                    return when(Confirmation.values()[confirmationChoice - 1]) {
                        Confirmation.CONTINUE -> true
                        Confirmation.GO_BACK -> false
                    }
                } else {
                    println("Enter proper input: ")
                }
            } catch(exception: Exception) {
                println("Class Helper: confirm(): Exception: $exception")
            }
        }
    }

    fun fieldValidation(fieldValue: String): Boolean {
        return fieldValue == ""
    }

    fun checkValidRecord(option: Int, size: Int): Boolean {
        return option != 0 && option <= size
    }

    fun validatePassword(password: String, rePassword: String): Boolean {
        return password != rePassword
    }

    fun generateOTP(): String {
        return Random.nextInt(100000,1000000).toString()
    }

    fun verifyOtp(currentOtp: String, generatedOtp: String): Boolean {
        return currentOtp == generatedOtp
    }

    fun confirmPassword(rePassword: String, password: String): Boolean {
        return rePassword == password
    }

    fun validateMobileNumber(number: String): Boolean { // 10-digit Phone number with Country Code Prefix(max 3 characters)
        return Pattern.matches("^(\\+\\d{1,3}( )?)?\\d{10}$", number)
    }

    fun validateEmail(email: String): Boolean {
        return Pattern.matches("^[a-zA-Z0-9_!#$.-]+@[a-zA-Z0-9.-]+\$", email)
    }

    fun validatePasswordPattern(password: String): Boolean {
        return Pattern.matches("^[a-zA-Z0-9!#@$%^&*_+`~]{4,8}+$", password)
        // return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,25}$", password)
    }
}

fun main() {

    println(Helper.generateOTP())
    println(Helper.validateMobileNumber("+0")) // false
    println(Helper.validateMobileNumber("+91 9080440195")) // true
    println(Helper.validateMobileNumber("9080440195")) // true
    println(Helper.validateMobileNumber("+123 9080440195")) // true
    println(Helper.validateMobileNumber("+12349080440195")) // false
    println(Helper.validateEmail("123_!#$.-@1234.234455")) // true
    println(Helper.validateEmail("123_!#$%&/.-@1234.234$455")) // false
    println(Helper.validateEmail("sri@gmail.com")) // true
    println(Helper.validatePasswordPattern("")) // false
    println(Helper.validatePasswordPattern("@Sri2609!@#")) // false
    println(Helper.validatePasswordPattern("COMPANY")) // TRUE

}

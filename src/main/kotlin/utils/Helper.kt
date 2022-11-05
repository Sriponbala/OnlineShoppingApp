package utils

import enums.Confirmation
import java.util.regex.Pattern
import kotlin.random.Random

object Helper {


    private var productId = 1

    private var orderId = 1
    private var recordId = 1


    fun generateProductId(): String {
        return "Product${productId++}"
    }


    fun generateOrderId(): String {
        return "Order${orderId++}"
    }

    fun generateRecordId(): String {
        return "Record${recordId++}"
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

    fun confirmPassword(confirmPassword: String, password: String): Boolean {
        return confirmPassword == password
    }

    fun validateMobileNumber(number: String): Boolean { // 10-digit Phone number with Country Code Prefix(max 3 characters)
        return Pattern.matches("^(\\+\\d{1,3}( )?)?\\d{10}$", number)
    }

    fun validateEmail(email: String): Boolean {
        return Pattern.matches("^[a-z0-9_!#$.-]{3,15}+@[a-z]{5,8}+.[a-z]{2,3}+\$", email)
    }

    fun validatePasswordPattern(password: String): Boolean {
        return Pattern.matches("^[a-zA-Z0-9!#@$%^&*_+`~]{4,8}+$", password)
        // return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,25}$", password)
    }

    fun validatePincode(pincode: String): Boolean {
        return Pattern.matches("^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$", pincode)
    }

    fun validateAddressFields(fieldValue: String): Boolean {
        return Pattern.matches("^[a-zA-Z1-9]{1}[a-zA-Z0-9-\\s]{0,30}$", fieldValue) // a-zA-Z]{1,20}[0-9]{0,3}\s{0,1}(- )?[a-zA-Z]{2,20}[0-9]{0,6}
    }
}

fun main() {

//    println(Helper.generateOTP())
//    println(Helper.validateMobileNumber("+0")) // false
//    println(Helper.validateMobileNumber("+91 9080440195")) // true
//    println(Helper.validateMobileNumber("9080440195")) // true
//    println(Helper.validateMobileNumber("+123 9080440195")) // true
//    println(Helper.validateMobileNumber("+12349080440195")) // false
//    println(Helper.validateEmail("123_!#$.-@1234.234455")) // true
//    println(Helper.validateEmail("123_!#$%&/.-@1234.234$455")) // false
//    println(Helper.validateEmail("sri@gmail.com")) // true
//    println(Helper.validatePasswordPattern("")) // false
//    println(Helper.validatePasswordPattern("@Sri2609!@#")) // false
//    println(Helper.validatePasswordPattern("COMPANY")) // TRUE
    println(Helper.validatePincode("600062")) // true
    println(Helper.validatePincode("600 062")) // true
    println(Helper.validateAddressFields("B1-S3")) // true
    println(Helper.validateAddressFields("abcd")) // true
    println(Helper.validateAddressFields("thirumullaivoyal")) // true
    println(Helper.validateAddressFields("chennai")) // true
    println(Helper.validateAddressFields("west mada street")) // true
    println(Helper.validateAddressFields("abcDE123 - aZ123456")) // true
    println(Helper.validateAddressFields("0")) // false
    println(Helper.validateAddressFields("1")) // true
}

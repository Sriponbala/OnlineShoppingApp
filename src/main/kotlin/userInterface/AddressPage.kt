package userInterface

import backend.UserAccountActivities
import data.Address
import enums.AddressSelectionOptions
import utils.Helper

class AddressPage(private val userAccountActivities: UserAccountActivities) {

    private val addresses = userAccountActivities.getUserAddresses()
    private var doorNo = ""
    private var flatName = ""
    private var street = ""
    private var area = ""
    private var city = ""
    private var state = ""
    private var pincode = ""
    private var addressesMap = mutableMapOf<Int, String>()

    private fun generateAddressMap(sno: Int, addressId: String) {
        addressesMap[sno] = addressId
    }

    fun displayAllAddresses() {
        var sno = 1
        for((addressId, address) in addresses) {
            generateAddressMap(sno, addressId)
            println("${sno++}. $address")
        }
    }

    fun showDashboard() {

        while (true) {
            println("------------Your Addresses--------------")
            for(element in AddressSelectionOptions.values()) {
                println("${element.ordinal+1}. $element")
            }
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, AddressSelectionOptions.values().size)) {
                    if(doDashboardActivities(AddressSelectionOptions.values()[dashBoardOption-1])) {
                        break
                    }
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class AddressPage: showDashBoard(): Exception: $exception")
            }
        }
    }

    private fun doDashboardActivities(enumConstant: AddressSelectionOptions): Boolean {

        return when(enumConstant) {
            AddressSelectionOptions.ADD_NEW_ADDRESS -> {
                println("Fill address field: ")
                getUserInputs()
                userAccountActivities.addNewAddress(doorNo, flatName, street, area, city, state, pincode)
                false
            }

            AddressSelectionOptions.SAVED_ADDRESS -> {
                editAddress(selectAnAddress())
                false
            }

            AddressSelectionOptions.GO_BACK -> {
                true
            }
        }
    }

    private fun getUserInputs() {

        do{
            println("Enter door number: ")
            doorNo = readLine()!!
        } while(Helper.fieldValidation(doorNo) || !Helper.validateAddressFields(doorNo))

        do{
            println("Enter flat name: ")
            flatName = readLine()!!
        } while(Helper.fieldValidation(flatName) || !Helper.validateAddressFields(flatName))

        do{
            println("Enter street: ")
            street = readLine()!!
        } while(Helper.fieldValidation(street) || !Helper.validateAddressFields(street))

        do{
            println("Enter area: ")
            area = readLine()!!
        } while(Helper.fieldValidation(area) || !Helper.validateAddressFields(area))

        do{
            println("Enter city: ")
            city = readLine()!!
        } while(Helper.fieldValidation(city) || !Helper.validateAddressFields(city))

        do{
            println("Enter state: ")
            state = readLine()!!
        } while(Helper.fieldValidation(state) || !Helper.validateAddressFields(state))

        do{
            println("Enter pincode: ")
            pincode = readLine()!!
        } while(Helper.fieldValidation(pincode) || !Helper.validatePincode(pincode))

    }

    private fun editAddress(addressId: String?) {
        // delete or edit

    }

    private fun selectAnAddress(): String? {
        var option = 0
        while(true){
            println("Select an address: ")
            try{
                var userInput = readLine()!!
                option = userInput.toInt()
                return if(Helper.checkValidRecord(option,addressesMap.size)) addressesMap[option] else null
            } catch(exception: Exception) {
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
    }

}
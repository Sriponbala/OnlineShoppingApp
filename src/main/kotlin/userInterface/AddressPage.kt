package userInterface

import backend.UserAccountActivities
import data.Address
import enums.AddressFields
import enums.AddressManagementOptions
import enums.AddressSelectionOptions
import utils.Helper

class AddressPage(private val userAccountActivities: UserAccountActivities) {

    private lateinit var addresses: MutableMap<String, Address>
    private var doorNo = ""
    private var flatName = ""
    private var street = ""
    private var area = ""
    private var city = ""
    private var state = ""
    private var pincode = ""
    private val addressesMap = mutableMapOf<Int, String>()
    private var selectAddress = false
    private var shippingAddress: String = ""

    fun setSelectAddress(input: Boolean) {
        this.selectAddress = input
    }

    fun selectAddressForDelivery(): String {
        do{
            openAddressPage()
        } while(shippingAddress == "" && selectAddress)
        return shippingAddress
    }

    fun openAddressPage() {

        this.addresses = userAccountActivities.getUserAddresses()
        displayAllAddresses()
        val addressSelectionOptions = AddressSelectionOptions.values()
        while(true) {
            showDashboard("Address Page Dashboard", addressSelectionOptions)
            when(getUserChoice(addressSelectionOptions)) {
                AddressSelectionOptions.ADD_NEW_ADDRESS -> {
                    getUserInputs()
                    if(Helper.confirm()) {
                        addNewAddress()
                    }
                }
                AddressSelectionOptions.SAVED_ADDRESS -> {
                    while(true) {
                        displayAllAddresses()
                        if(selectAddress) {
                            if(addresses.isEmpty()) {
                                break
                            } else{
                                shippingAddress = userAccountActivities.getShippingAddress(selectAnAddress())
                                break
                            }
                        }
                        if(manageAddress()) {
                            break
                        }
                    }
                }
                AddressSelectionOptions.GO_BACK -> {
                    selectAddress = false
                    break
                }
            }
        }
    }

    private fun displayAllAddresses() {

        if(addresses.isEmpty()) {
            println("        EMPTY ADDRESS LIST       ")
        } else {
            var sno = 1
            println("-----------------YOUR ADDRESSES------------------")
            for((addressId, address) in addresses) {
                generateAddressMap(sno, addressId)
                println("${sno++}. $address")
            }
        }
    }

    private fun generateAddressMap(sno: Int, addressId: String) {
        addressesMap[sno] = addressId
    }

    private fun addNewAddress() {
        userAccountActivities.addNewAddress(doorNo, flatName, street, area, city, state, pincode)
    }

    private fun manageAddress(): Boolean {

        // delete or edit
        if(addresses.isEmpty()) {
            return true
        } else {
            val addressId = selectAnAddress()
            val addressManagementOptions = AddressManagementOptions.values()
            while(true) {
                showDashboard("Address management options", addressManagementOptions)
                return when(getUserChoice(addressManagementOptions)) {
                    AddressManagementOptions.EDIT -> { // edit address
                        editAddress(addressId)
                        false
                    }
                    AddressManagementOptions.DELETE -> { // delete address
                        deleteAddress(addressId)
                        true
                    }
                    AddressManagementOptions.BACK ->  true
                }
            }
        }
    }

    private fun editAddress(addressId: String) {

        val addressFields = AddressFields.values()
        while(true) {
            showDashboard("Address fields", addressFields)
            when(getUserChoice(addressFields)) {
                AddressFields.DOORNUMBER -> {
                    userAccountActivities.updateAddress(addressId, "doorNo", getUserInput("door number"))
                }
                AddressFields.FLATNAME -> {
                    userAccountActivities.updateAddress(addressId, "flatName", getUserInput("flat name"))
                }
                AddressFields.STREET -> {
                    userAccountActivities.updateAddress(addressId, "street", getUserInput("street name"))
                }
                AddressFields.AREA -> {
                    userAccountActivities.updateAddress(addressId, "area", getUserInput("area name"))
                }
                AddressFields.CITY -> {
                    userAccountActivities.updateAddress(addressId, "city", getUserInput("city name"))
                }
                AddressFields.STATE -> {
                    userAccountActivities.updateAddress(addressId, "state", getUserInput("state name"))
                }
                AddressFields.PINCODE -> {
                    userAccountActivities.updateAddress(addressId, "pincode", getPincode())
                }
                AddressFields.BACK -> {
                    break
                }
            }
        }
    }

    private fun deleteAddress(addressId: String) {

        userAccountActivities.deleteAddress(addressId)
    }

    private fun selectAnAddress(): String {

        var option: Int
        var selectedAddress: String
        while(true){
            println("SELECT AN ADDRESS: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option,addressesMap.size)) {
                    println("SELECTED ADDRESS: ${addresses[addressesMap[option]]}")
                    selectedAddress = addressesMap[option]!!
                    break
                } else {
                    println("Invalid option! Try again")
                }
            } catch(exception: Exception) {
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedAddress
    }

    private fun <E: Enum<E>> showDashboard(title: String, enumArray: Array<E>) {

        println("-------------${title.uppercase()}-------------")
        for(element in enumArray) {
            println("${element.ordinal+1}. $element")
        }
    }
    private fun <E: Enum<E>> getUserChoice(enumArray: Array<E>): E {

        while (true) {
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
                    return enumArray[dashBoardOption-1]
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class AddressPage: getUserChoice(): Exception: $exception")
            }
        }
    }

    private fun getUserInputs() {

        println("FILL ADDRESS FIELDS: ")

        do{
            println("ENTER DOOR NUMBER: ")
            doorNo = readLine()!!
        } while(Helper.fieldValidation(doorNo) || !Helper.validateAddressFields(doorNo))

        do{
            println("ENTER FLAT  NAME: ")
            flatName = readLine()!!
        } while(Helper.fieldValidation(flatName) || !Helper.validateAddressFields(flatName))

        do{
            println("ENTER STREET NAME: ")
            street = readLine()!!
        } while(Helper.fieldValidation(street) || !Helper.validateAddressFields(street))

        do{
            println("ENTER AREA NAME: ")
            area = readLine()!!
        } while(Helper.fieldValidation(area) || !Helper.validateAddressFields(area))

        do{
            println("ENTER CITY NAME: ")
            city = readLine()!!
        } while(Helper.fieldValidation(city) || !Helper.validateAddressFields(city))

        do{
            println("ENTER STATE NAME: ")
            state = readLine()!!
        } while(Helper.fieldValidation(state) || !Helper.validateAddressFields(state))

        do{
            println("ENTER PINCODE: ")
            pincode = readLine()!!
        } while(Helper.fieldValidation(pincode) || !Helper.validatePincode(pincode))
    }

    private fun getUserInput(message: String = ""): String {

        var userInput: String
        do {
            println("ENTER ${message.uppercase()}: ")
            userInput = readLine()!!
        } while(Helper.fieldValidation(userInput) || !Helper.validateAddressFields(userInput))
        return userInput
    }

    private fun getPincode(): String {

        var pincode: String
        do {
            println("ENTER PINCODE: ")
            pincode = readLine()!!
        } while(Helper.fieldValidation(pincode) || !Helper.validatePincode(pincode))
        return pincode
    }

}



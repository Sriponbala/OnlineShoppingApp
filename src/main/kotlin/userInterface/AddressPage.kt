package userInterface

import backend.UserAccountActivities
import data.Address
import enums.AddressFields
import enums.AddressManagementOptions
import enums.AddressSelectionOptions
import interfaces.DashboardServices
import interfaces.OnboardingServices
import utils.Helper

class AddressPage(private val userAccountActivities: UserAccountActivities): DashboardServices, OnboardingServices {

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

    fun deselectShippingAddress() {
        this.shippingAddress = ""
    }

    fun openAddressPage() {
        this.addresses = userAccountActivities.getUserAddresses()
        displayAllAddresses()
        val addressSelectionOptions = AddressSelectionOptions.values()
        while(true) {
            super.showDashboard("ADDRESS PAGE DASHBOARD", addressSelectionOptions)
            when(super.getUserChoice(addressSelectionOptions)) {

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
        if(this.addresses.isEmpty()) {
            println("        No address found       ")
        } else {
            var sno = 1
            println("-----------------YOUR ADDRESSES------------------")
            println("Default Shipping address: $shippingAddress")
            for((addressId, address) in addresses) {
                generateAddressMap(sno, addressId)
                println("""${sno++}. ${address.doorNo}, ${address.flatName},
                    |   ${address.street}, ${address.area},
                    |   ${address.city}, ${address.state} - ${address.pincode}
                """.trimMargin())
            }
        }
    }

    private fun generateAddressMap(sno: Int, addressId: String) {
        addressesMap[sno] = addressId
    }

    private fun addNewAddress() {
       if(userAccountActivities.addNewAddress(doorNo, flatName, street, area, city, state, pincode)) {
           println("Address added!")
       } else {
           println("Failed to add address!")
       }
    }

    private fun manageAddress(): Boolean {
        // delete or edit
        if(addresses.isEmpty()) {
            return true
        } else {
            val addressId = selectAnAddress()
            val addressManagementOptions = AddressManagementOptions.values()
            while(true) {
                super.showDashboard("ADDRESS MANAGEMENT OPTIONS", addressManagementOptions)
                return when(super.getUserChoice(addressManagementOptions)) {
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
            super.showDashboard("ADDRESS FIELDS", addressFields)
            when(super.getUserChoice(addressFields)) {
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
        if(userAccountActivities.deleteAddress(addressId)) {
            println("Address deleted!")
        } else println("Failed to delete address!")
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


    override fun getUserInputs() {
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



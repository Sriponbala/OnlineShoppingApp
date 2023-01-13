package userInterface

import backend.UserAccountActivities
import data.Address
import enums.AddressField
import enums.AddressManagement
import enums.AddressSelection
import interfaces.DashboardServices
import utils.Helper

class AddressPage(private val userAccountActivities: UserAccountActivities): DashboardServices {

    private lateinit var addresses: List<Address>
    private var doorNo = ""
    private var flatName = ""
    private var street = ""
    private var area = ""
    private var city = ""
    private var state = ""
    private var pincode = ""
    private var selectAddress = false
    private var shippingAddress: Address? = null

    fun setSelectAddress(input: Boolean) {
        this.selectAddress = input
    }

    fun selectAddressForDelivery(): Address? {
        do{
            openAddressPage()
        } while(shippingAddress == null && selectAddress)
        return shippingAddress
    }

    fun deselectShippingAddress() {
        this.shippingAddress = null
    }

    fun openAddressPage() {
        this.addresses = userAccountActivities.getUserAddresses()
        displayAllAddresses()
        val addressSelectionOptions = AddressSelection.values()
        while(true) {
            super.showDashboard("ADDRESS PAGE DASHBOARD", addressSelectionOptions)
            when(super.getUserChoice(addressSelectionOptions)) {

                AddressSelection.ADD_NEW_ADDRESS -> {
                    getUserInputs()
                    if(Helper.confirm()) {
                        addNewAddress()
                    }
                }

                AddressSelection.SAVED_ADDRESS -> {
                    this.addresses = userAccountActivities.getUserAddresses()
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

                AddressSelection.GO_BACK -> {
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
            for(address in addresses) {
                println("""${sno++}. ${address.doorNo}, ${address.flatName},
                    |   ${address.street}, ${address.area},
                    |   ${address.city}, ${address.state} - ${address.pincode}
                """.trimMargin())
            }
        }
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
            val addressManagementOptions = AddressManagement.values()
            while(true) {
                super.showDashboard("ADDRESS MANAGEMENT OPTIONS", addressManagementOptions)
                return when(super.getUserChoice(addressManagementOptions)) {
                    AddressManagement.EDIT -> { // edit address
                        editAddress(addressId)
                        false
                    }
                    AddressManagement.DELETE -> { // delete address
                        deleteAddress(addressId)
                        true
                    }
                    AddressManagement.BACK ->  true
                }
            }
        }
    }

    private fun editAddress(addressId: String) {
        val addressFields = AddressField.values()
        while(true) {
            super.showDashboard("ADDRESS FIELDS", addressFields)
            when(super.getUserChoice(addressFields)) {
                AddressField.DOORNUMBER -> {
                    userAccountActivities.updateAddress(addressId, AddressField.DOORNUMBER, getUserInput("door number"))
                }
                AddressField.FLATNAME -> {
                    userAccountActivities.updateAddress(addressId, AddressField.FLATNAME, getUserInput("flat name"))
                }
                AddressField.STREET -> {
                    userAccountActivities.updateAddress(addressId, AddressField.STREET, getUserInput("street name"))
                }
                AddressField.AREA -> {
                    userAccountActivities.updateAddress(addressId, AddressField.AREA, getUserInput("area name"))
                }
                AddressField.CITY -> {
                    userAccountActivities.updateAddress(addressId, AddressField.CITY, getUserInput("city name"))
                }
                AddressField.STATE -> {
                    userAccountActivities.updateAddress(addressId, AddressField.STATE, getUserInput("state name"))
                }
                AddressField.PINCODE -> {
                    userAccountActivities.updateAddress(addressId, AddressField.PINCODE, getPincode())
                }
                AddressField.BACK -> {
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
                if(Helper.checkValidRecord(option,addresses.size)) {
                    selectedAddress = addresses[option - 1].addressId
                    break
                } else {
                    println("Invalid option! Try again")
                }
            } catch(exception: Exception) {
                println("Invalid option! Try again!")
            }
        }
        return selectedAddress
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



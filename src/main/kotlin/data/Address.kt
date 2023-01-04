package data

import utils.Helper

data class Address(var doorNo: String, var flatName: String, var street: String, var area: String, var city: String, var state: String, var pincode: String) {
    val addressId: String = Helper.generateAddressId()
}

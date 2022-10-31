package data

data class Address(var doorNo: String, var flatName: String, var street: String, var area: String, var city: String, var state: String, var country: String) {
    override fun toString(): String {
        return "$doorNo, $flatName, $street, $area, $city, $state, $country"
    }
}

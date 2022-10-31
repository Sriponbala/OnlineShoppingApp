package data

sealed class Product {

    data class Book(val productId: String, val productName: String, val price: Float, var availableQuantity: Int, var requiredQuantity: Int, val bookType: String, val category: String = "book"): Product()
    data class Mobile(val productId: String, val productName: String, val price: Float, var availableQuantity: Int, var requiredQuantity: Int, val brand: String, val storage: String, val category: String = "mobile"): Product()
    data class Clothing(val productId: String, val productName: String, val price: Float, var availableQuantity: Int, var requiredQuantity: Int, val gender: String, val colour: String, val category: String = "clothing"): Product()
    data class Earphone(val productId: String, val productName: String, val price: Float, var availableQuantity: Int, var requiredQuantity: Int, val type: String, val colour: String, val category: String = "book"): Product()
}
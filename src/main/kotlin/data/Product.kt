package data

import utils.Helper

sealed class Product {

    abstract var productId: String
    abstract  var productName: String
    abstract var price: Float
    abstract var availableQuantity: Int
    abstract var requiredQuantity: Int

    data class Book(override var productId: String, override var productName: String, override var price: Float, override var availableQuantity: Int, override var requiredQuantity: Int, var bookType: String, var category: String = "book"): Product()
    data class Mobile(override var productId: String, override var productName: String, override var price: Float, override var availableQuantity: Int, override var requiredQuantity: Int, var brand: String, var storage: String, val category: String = "mobile"): Product()
    data class Clothing(override var productId: String, override var productName: String, override var price: Float, override var availableQuantity: Int, override var requiredQuantity: Int, var gender: String, var colour: String, val category: String = "clothing"): Product()
    data class Earphone(override var productId: String, override var productName: String, override var price: Float, override var availableQuantity: Int, override var requiredQuantity: Int, var type: String, var colour: String, val category: String = "book"): Product()

}



//abstract class AbstractClassProduct {
//
//    open lateinit var productId: String
//    open lateinit var productName: String
//    open var price: Float = 0f
//    open var availableQuantity: Int = 0
//    open var requiredQuantity: Int = 0
//
//}
//
//class Book(override var productId: String,
//           override var productName: String,
//           override var price: Float,
//           override var availableQuantity: Int,
//           override var requiredQuantity: Int,
//           var bookType: String,
//           val category: String = "book"): AbstractClassProduct() {
//
//
//}
//
//fun demo(product: AbstractClassProduct) {
//    println("${product.productName}")
//}
//
//fun main() {
//    demo(Book(Helper.generateProductId(), "Book1", 200f, 5, 0, "fiction"))
//}
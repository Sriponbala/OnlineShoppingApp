package data

import enums.ProductStatus

sealed class Product {

    abstract val productId: String
    abstract val productName: String
    abstract val price: Float
    abstract val category: String
    abstract var availableQuantity: Int
    abstract var status: ProductStatus

    data class Book(override val productId: String, override val productName: String, override val price: Float, override var availableQuantity: Int, val bookType: String, override val category: String = "book", override var status: ProductStatus): Product()
    data class Mobile(override val productId: String, override val productName: String, override val price: Float, override var availableQuantity: Int, val brand: String, val storage: String, override val category: String = "mobile", override var status: ProductStatus): Product()
    data class Clothing(override val productId: String, override val productName: String, override val price: Float, override var availableQuantity: Int, val gender: String, val colour: String, override val category: String = "clothing", override var status: ProductStatus): Product()
    data class Earphone(override val productId: String, override val productName: String, override val price: Float, override var availableQuantity: Int, val type: String, val colour: String, val brand: String, override val category: String = "earphone", override var status: ProductStatus): Product()

}




















//abstract class AbstractClassProduct {
//
//    open late init var productId: String
//    open late init var productName: String
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
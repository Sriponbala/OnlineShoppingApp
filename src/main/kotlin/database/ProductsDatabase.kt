package database

import data.Product
import enums.ProductStatus

//class ProductsDatabase private constructor() {
//
//
//    companion object {
//        private val INSTANCE by lazy { ProductsDatabase() }
//        fun getInstance(userName: String, password: String): ProductsDatabase? {
//
//            return if (userName == "root" && password == "tiger") {
//                INSTANCE
//            } else {
//                null
//            }
//        }
//    }

object ProductsDatabase {

    val products: Map<String,MutableList<Product>>

    private val book1 = Product.Book(generateProductId(), "Book1", 200f, 5, "fiction", status = ProductStatus.IN_STOCK)
    private val book2 = Product.Book(generateProductId(), "Book2", 110f, 10, "nonfiction", status = ProductStatus.IN_STOCK)
    private val book3 = Product.Book(generateProductId(), "Book3", 200f, 7, "fiction", status = ProductStatus.IN_STOCK)
    private val book4 = Product.Book(generateProductId(), "Book4", 250f, 5, "nonfiction", status = ProductStatus.IN_STOCK)
    private val book5 = Product.Book(generateProductId(), "Book5", 250f, 1, "nonfiction", status = ProductStatus.IN_STOCK)
    private val books = mutableListOf<Product>(book1, book2, book3, book4, book5)

    private val iphone14 =
        Product.Mobile(generateProductId(), "iPhone 14 128GB", 79900f, 5, "Apple", "128 GB", status = ProductStatus.IN_STOCK)
    private val samsungGalaxyM33 =
        Product.Mobile(generateProductId(), "Samsung Galaxy M33 5G", 15499f, 9, "Samsung", "128 GB", status = ProductStatus.IN_STOCK)
    private val samsungGalaxyS20 =
        Product.Mobile(generateProductId(), "Samsung Galaxy S20 FE 5G", 29900f, 4, "Samsung", "128 GB", status = ProductStatus.IN_STOCK)
    private val mobiles = mutableListOf<Product>(iphone14,samsungGalaxyM33,samsungGalaxyS20)

    private val clothings = mutableListOf<Product>()

    private val earphones = mutableListOf<Product>()
    init {
        products = mutableMapOf("book" to books, "mobile" to mobiles, "clothing" to clothings, "earphone" to earphones)
    }

    private var productId = 1

    private fun generateProductId(): String {
        return "Product${productId++}"
    }

}


package database

import data.Product
import utils.Helper

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

    private val book1 = Product.Book(Helper.generateProductId(), "Book1", 200f, 5, 0, "fiction")
    private val book2 = Product.Book(Helper.generateProductId(), "Book2", 110f, 10, 0, "nonfiction")
    private val book3 = Product.Book(Helper.generateProductId(), "Book3", 200f, 7, 0, "fiction")
    private val book4 = Product.Book(Helper.generateProductId(), "Book4", 250f, 5, 0, "nonfiction")
    private val book5 = Product.Book(Helper.generateProductId(), "Book5", 250f, 1, 0, "nonfiction")
    private val books = mutableListOf<Product>(book1, book2, book3, book4, book5)

    private val iphone14 =
        Product.Mobile(Helper.generateProductId(), "iPhone 14 128GB", 79900f, 5, 0, "Apple", "128 GB")
    private val samsungGalaxyM33 =
        Product.Mobile(Helper.generateProductId(), "Samsung Galaxy M33 5G", 15499f, 9, 0, "Samsung", "128 GB")
    private val samsungGalaxyS20 =
        Product.Mobile(Helper.generateProductId(), "Samsung Galaxy S20 FE 5G", 29900f, 4, 0, "Samsung", "128 GB")
    private val mobiles = mutableListOf<Product>(iphone14,samsungGalaxyM33,samsungGalaxyS20)

    private val clothings = mutableListOf<Product>()

    private val earphones = mutableListOf<Product>()
    init {
        products = mutableMapOf("books" to books, "mobiles" to mobiles, "clothings" to clothings, "earphones" to earphones)
    }

}


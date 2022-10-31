package data

import utils.Helper

object Products {

    private var products: MutableMap<String,MutableList<Product>>

    private val book1 = Product.Book(Helper.generateProductId(), "Book1", 200f, 5, 0, "fiction")
    private val book2 = Product.Book(Helper.generateProductId(), "Book2", 110f, 10, 0, "nonfiction")
    private val book3 = Product.Book(Helper.generateProductId(), "Book3", 200f, 7, 0, "fiction")
    private val book4 = Product.Book(Helper.generateProductId(), "Book4", 250f, 5, 0, "nonfiction")
    private val book5 = Product.Book(Helper.generateProductId(), "Book5", 250f, 1, 0, "nonfiction")
    private val books = mutableListOf<Product>(book1, book2, book3, book4, book5)

    private val iphone14 = Product.Mobile(Helper.generateProductId(),"iPhone 14 128GB",79900f, 5, 0,"Apple", "128 GB")
    private val samsungGalaxyM33 = Product.Mobile(Helper.generateProductId(),"Samsung Galaxy M33 5G",15499f, 9, 0, "Samsung", "128 GB")
    private val samsungGalaxyS20 = Product.Mobile(Helper.generateProductId(),"Samsung Galaxy S20 FE 5G",29900f, 4, 0, "Samsung", "128 GB")
    private val mobiles = mutableListOf<Product>(iphone14,samsungGalaxyM33,samsungGalaxyS20)

    private val clothings = mutableListOf<Product>()

    private val earphones = mutableListOf<Product>()
    init {
        products = mutableMapOf("Books" to books, "Mobiles" to mobiles, "Clothings" to clothings, "Earphones" to earphones)
    }

    fun getProducts() = products

}

/* companion object{
         private val databaseInstance: Products by lazy {
             Products()
         }
         fun getInstance() = databaseInstance
   } */

package database

import data.Product
import enums.ProductStatus

object ProductsTable {

    val products: Map<String,MutableList<Product>>
    private var productId = 1

    private val book1 = Product.Book(generateProductId(), "Invisible Pain - The cry of nature!!!", 64f, 5, "fiction", status = ProductStatus.IN_STOCK)
    private val book2 = Product.Book(generateProductId(), "The Power of Positive Thinking", 153f, 10, "nonfiction", status = ProductStatus.IN_STOCK)
    private val book3 = Product.Book(generateProductId(), "400 Days", 133f, 7, "fiction", status = ProductStatus.IN_STOCK)
    private val book4 = Product.Book(generateProductId(), "One Day, Life Will Change", 133f, 5, "nonfiction", status = ProductStatus.IN_STOCK)
    private val book5 = Product.Book(generateProductId(), "Ponniyin Selvan - Part 2", 442f, 1, "fiction", status = ProductStatus.IN_STOCK)
    private val books = mutableListOf<Product>(book1, book2, book3, book4, book5)


    private val iphone14 =
        Product.Mobile(generateProductId(), "iPhone 14 128GB", 79900f, 5, "Apple", "128 GB", status = ProductStatus.IN_STOCK)
    private val samsungGalaxyM33 =
        Product.Mobile(generateProductId(), "Samsung Galaxy M33 5G", 15499f, 9, "Samsung", "128 GB", status = ProductStatus.IN_STOCK)
    private val samsungGalaxyS20 =
        Product.Mobile(generateProductId(), "Samsung Galaxy S20 FE 5G", 29900f, 4, "Samsung", "128 GB", status = ProductStatus.IN_STOCK)
    private val mobiles = mutableListOf<Product>(iphone14, samsungGalaxyM33, samsungGalaxyS20)


    private val kurti = Product.Clothing(generateProductId(), "Kurti", 1500f, 15, "female", "yellow", status = ProductStatus.IN_STOCK)
    private val formalShirt = Product.Clothing(generateProductId(), "Formal Shirt", 700f, 7, "male", "cream", status = ProductStatus.IN_STOCK)
    private val lehengaCholi = Product.Clothing(generateProductId(), "Lehenga Choli", 3000f, 3, "female", "red", status = ProductStatus.IN_STOCK)
    private val tShirt = Product.Clothing(generateProductId(), "T-Shirt", 1500f, 10, "male", "black", status = ProductStatus.IN_STOCK)
    private val clothings = mutableListOf<Product>(kurti, formalShirt, lehengaCholi, tShirt)


    private val boAtBassHeads100 = Product.Earphone(generateProductId(), "boAt BassHeads 100", 379f, 9, "wired", "black", "boAt", status = ProductStatus.IN_STOCK)
    private val zebronicsZebBro = Product.Earphone(generateProductId(), "ZEBRONICS Zeb-Bro", 149f, 14, "wired", "red", "zebronics", status = ProductStatus.IN_STOCK)
    private val abortEarbudProTune = Product.Earphone(generateProductId(), "Abort Earbud Pro Tune", 749f, 2, "wireless", "red", "generic", status = ProductStatus.IN_STOCK)
    private val earphones = mutableListOf<Product>(boAtBassHeads100, zebronicsZebBro, abortEarbudProTune)


    init {
        products = mutableMapOf("book" to books, "mobile" to mobiles, "clothing" to clothings, "earphone" to earphones)
    }

    private fun generateProductId(): String {
        return "Product${productId++}"
    }

}


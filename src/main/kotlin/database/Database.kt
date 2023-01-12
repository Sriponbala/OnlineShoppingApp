package database

import data.*
import enums.BookType
import enums.Brand
import enums.Colour
import enums.Gender

class Database private constructor() {

    companion object {
        private val INSTANCE by lazy { Database() }
        fun getConnection(userName: String, password: String): Database? {
            return if(userName == "root" && password == "tiger") {
                INSTANCE
            } else null
        }
    }

    val users: MutableList<User> = mutableListOf()
    val usersAccountInfo: MutableList<AccountInfo> = mutableListOf()
    val addresses: MutableList<Address> = mutableListOf()
    val usersPassword: MutableList<UserPassword> = mutableListOf()
    val carts: MutableList<Cart> = mutableListOf()
    val cartItems: MutableList<CartItem> = mutableListOf()
    val usersWishList: MutableList<WishList> = mutableListOf()
    val wishListItems: MutableList<WishListItem> = mutableListOf()
    val lineItems: MutableList<LineItem> = mutableListOf()
    val orders: MutableList<Order> = mutableListOf()
    val orderLineItemMappings: MutableList<OrderIdLineItemMapping> = mutableListOf()

    val productSkus: MutableList<ProductSku> = mutableListOf()
    val productsInfo: MutableList<ProductInfo> = mutableListOf()
    val stocks: MutableList<StockAvailability> = mutableListOf()


    private val book1_1 = ProductSku.Book("BKIPTCON", "Invisible Pain - The cry of nature!!!", 64f, BookType.FICTION)
    private val book2 = ProductSku.Book("BKTPOPT","The Power of Positive Thinking", 153f, BookType.NONFICTION)
    private val book3 = ProductSku.Book("BK400D","400 Days", 133f, BookType.FICTION)
    private val book4 = ProductSku.Book("BKODLWC","One Day, Life Will Change", 133f, BookType.NONFICTION)
    private val book5 = ProductSku.Book("BKPS02","Ponniyin Selvan - Part 2", 442f, BookType.FICTION)


    private val iphone14_1 =
        ProductSku.Mobile("MIP14","iPhone 14 128GB", 79900f, Brand.APPLE)
    private val samsungGalaxyM33 =
        ProductSku.Mobile("SGM335G","Samsung Galaxy M33 5G", 15499f, Brand.SAMSUNG)
    private val samsungGalaxyS20 =
        ProductSku.Mobile("SGS205G","Samsung Galaxy S20 FE 5G", 29900f, Brand.SAMSUNG)


    private val kurti_1 = ProductSku.Clothing("KRIWN","Kurti", 1500f, Gender.FEMALE, Colour.BLUE)
    private val formalShirt = ProductSku.Clothing("FSMEN","Formal Shirt", 700f, Gender.MALE, Colour.RED)
    private val lehengaCholi = ProductSku.Clothing("LCWN","Lehenga Choli", 3000f, Gender.FEMALE, Colour.RED)
    private val tShirt = ProductSku.Clothing("TSMEN","T-Shirt", 1500f, Gender.MALE, Colour.BLACK)


    private val boAtBassHeads100 = ProductSku.Earphone("BB100","boAt BassHeads 100", 379f, Brand.BOAT)
    private val zebronicsZebBro = ProductSku.Earphone("ZZBRO","ZEBRONICS Zeb-Bro", 149f, Brand.ZEBRONICS)
    private val abortEarbudProTune_1 = ProductSku.Earphone("ABEPT","Abort Earbud Pro Tune", 749f, Brand.GENERICS)

    init {
        productSkus.add(book1_1)
        productSkus.add(book2)
        productSkus.add(book3)
        productSkus.add(book4)
        productSkus.add(book5)
        productSkus.add(iphone14_1)
        productSkus.add(samsungGalaxyM33)
        productSkus.add(samsungGalaxyS20)
        productSkus.add(kurti_1)
        productSkus.add(lehengaCholi)
        productSkus.add(formalShirt)
        productSkus.add(tShirt)
        productSkus.add(boAtBassHeads100)
        productSkus.add(zebronicsZebBro)
        productSkus.add(abortEarbudProTune_1)

        stocks.add(StockAvailability("BKIPTCON", 3))
        stocks.add(StockAvailability("BKTPOPT", 1))
        stocks.add(StockAvailability("BK400D", 1))
        stocks.add(StockAvailability("BKODLWC", 1))
        stocks.add(StockAvailability("BKPS02", 1))
        stocks.add(StockAvailability("MIP14", 2))
        stocks.add(StockAvailability("SGM335G", 1))
        stocks.add(StockAvailability("SGS205G", 1))
        stocks.add(StockAvailability("KRIWN", 4))
        stocks.add(StockAvailability("FSMEN", 1))
        stocks.add(StockAvailability("LCWN", 1))
        stocks.add(StockAvailability("TSMEN", 1))
        stocks.add(StockAvailability("BB100", 1))
        stocks.add(StockAvailability("ZZBRO", 1))
        stocks.add(StockAvailability("ABEPT", 5))

    }
}
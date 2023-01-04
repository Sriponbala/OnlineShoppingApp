package database

import data.*

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
    val usersAddress: MutableList<UserAddress> = mutableListOf()
    val usersPassword: MutableList<UserPassword> = mutableListOf()
    val carts: MutableList<Cart> = mutableListOf()
    val cartItems: MutableList<CartItem> = mutableListOf()
    val usersWishList: MutableList<WishList> = mutableListOf()
    val wishListItems: MutableList<WishListItem> = mutableListOf()
    val lineItems: MutableList<LineItem> = mutableListOf()
    val orders: MutableList<Order> = mutableListOf()
    val orderLineItemMappings: MutableList<OrderIdLineItemMapping> = mutableListOf()
    val usersOrdersHistory: MutableList<OrdersHistory> = mutableListOf()

    val productsSku: MutableList<ProductSku> = mutableListOf()
    val productsDetails: MutableList<ProductDetails> = mutableListOf()
    val stocks: MutableList<StockAvailability> = mutableListOf()


    private val book1_1 = ProductSku.Book("BKIPTCON", "Invisible Pain - The cry of nature!!!", 64f, Filters.BookTypeFilters.Fiction())
    private val book2 = ProductSku.Book("BKTPOPT","The Power of Positive Thinking", 153f, Filters.BookTypeFilters.NonFiction())
    private val book3 = ProductSku.Book("BK400D","400 Days", 133f, Filters.BookTypeFilters.Fiction())
    private val book4 = ProductSku.Book("BKODLWC","One Day, Life Will Change", 133f, Filters.BookTypeFilters.NonFiction())
    private val book5 = ProductSku.Book("BKPS02","Ponniyin Selvan - Part 2", 442f, Filters.BookTypeFilters.Fiction())


    private val iphone14_1 =
        ProductSku.Mobile("MIP14","iPhone 14 128GB", 79900f, Filters.BrandFilters.Apple(), Filters.StorageFilters.Storage_128GB())
    private val samsungGalaxyM33 =
        ProductSku.Mobile("SGM335G","Samsung Galaxy M33 5G", 15499f, Filters.BrandFilters.Samsung(), Filters.StorageFilters.Storage_64GB() )
    private val samsungGalaxyS20 =
        ProductSku.Mobile("SGS205G","Samsung Galaxy S20 FE 5G", 29900f, Filters.BrandFilters.Samsung(), Filters.StorageFilters.Storage_128GB())


    private val kurti_1 = ProductSku.Clothing("KRIWN","Kurti", 1500f, Filters.GenderFilters.Female(), Filters.ColourFilters.Blue())
    private val formalShirt = ProductSku.Clothing("FSMEN","Formal Shirt", 700f, Filters.GenderFilters.Male(), Filters.ColourFilters.Red())
    private val lehengaCholi = ProductSku.Clothing("LCWN","Lehenga Choli", 3000f, Filters.GenderFilters.Female(), Filters.ColourFilters.Red())
    private val tShirt = ProductSku.Clothing("TSMEN","T-Shirt", 1500f, Filters.GenderFilters.Male(), Filters.ColourFilters.Black())


    private val boAtBassHeads100 = ProductSku.Earphone("BB100","boAt BassHeads 100", 379f,
        Filters.EarPhoneTypeFilters.Wired(), Filters.BrandFilters.Boat())
    private val zebronicsZebBro = ProductSku.Earphone("ZZBRO","ZEBRONICS Zeb-Bro", 149f, Filters.EarPhoneTypeFilters.Wired(), Filters.BrandFilters.Zebronics())
    private val abortEarbudProTune_1 = ProductSku.Earphone("ABEPT","Abort Earbud Pro Tune", 749f,
        Filters.EarPhoneTypeFilters.TrueWireless(), Filters.BrandFilters.Generics())

    init {
        productsSku.add(book1_1)
        productsSku.add(book2)
        productsSku.add(book3)
        productsSku.add(book4)
        productsSku.add(book5)
        productsSku.add(iphone14_1)
        productsSku.add(samsungGalaxyM33)
        productsSku.add(samsungGalaxyS20)
        productsSku.add(kurti_1)
        productsSku.add(lehengaCholi)
        productsSku.add(formalShirt)
        productsSku.add(tShirt)
        productsSku.add(boAtBassHeads100)
        productsSku.add(zebronicsZebBro)
        productsSku.add(abortEarbudProTune_1)

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
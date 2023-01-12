package userInterface

import backend.CartActivities
import backend.ProductActivities
import backend.WishListsActivities
import data.AccountInfo
import data.Filter
import data.ProductSku
import enums.*
import interfaces.DashboardServices
import utils.Helper

class ShopPage(private val productActivities: ProductActivities) : DashboardServices {

    private lateinit var wishListsActivities: WishListsActivities
    private lateinit var cartActivities: CartActivities
    private lateinit var checkOutPage: CheckOutPage
    private lateinit var addressPage: AddressPage
    private lateinit var paymentPage: PaymentPage
    private lateinit var productsList: List<Pair<ProductSku, StockStatus>>
    private lateinit var productSku: Pair<ProductSku, StockStatus>
    private var isEmptyProductsList = false
    private lateinit var accountInfo: AccountInfo
    private var isLoggedIn = false
    private lateinit var filterOption: FilterBy
    private lateinit var filter: Filter

    fun initializer(
        accountInfo: AccountInfo,
        wishListsActivities: WishListsActivities,
        cartActivities: CartActivities,
        checkOutPage: CheckOutPage,
        addressPage: AddressPage,
        paymentPage: PaymentPage
    ) {
        this.accountInfo = accountInfo
        this.isLoggedIn = true
        this.wishListsActivities = wishListsActivities
        this.cartActivities = cartActivities
        this.checkOutPage = checkOutPage
        this.addressPage = addressPage
        this.paymentPage = paymentPage
    }

    fun initializer(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
    }

    fun openShopPage() {
        label@while(true) {
            productsList = productActivities.getProductsList()
            isEmptyProductsList = productsList.isEmpty()
            if(isEmptyProductsList) {
                displayProducts(true)
                break
            } else {
                while(true) {
                    productsList = productActivities.getProductsList()
                    while(true) {
                        if(productsList.isEmpty()) {
                            displayProducts(true)
                        } else {
                            displayProducts(false)
                        }
                        val filterDashboard = FilterDashboard.values()
                        super.showDashboard("FILTER DASHBOARD", filterDashboard)
                        when(super.getUserChoice(filterDashboard)) {
                            FilterDashboard.APPLY_FILTER -> {
                                val categories = ProductCategory.values()
                                lateinit var category: ProductCategory
                                while(true) {
                                    if(Helper.confirm()) {
                                        println("Select a category:")
                                        super.showDashboard("CATEGORIES", categories)
                                        category = super.getUserChoice(categories)
                                        productsList = productActivities.getProductsList(category)
                                        println("Do you want to apply filters?")
                                        if(Helper.confirm()) {
                                            applyFilter(category)
                                            println("Filter applied!")
                                            break
                                        } else break
                                    } else break
                                }
                            }
                            FilterDashboard.CLEAR_FILTER -> {
                                productsList = productActivities.getProductsList()
                            }
                            FilterDashboard.BACK -> {
                                break
                            }
                        }
                    }
                    if(productsList.isEmpty()) break@label
                    if(Helper.confirm()) {
                        val skuId = selectAProduct()
                        if(Helper.confirm()) {
                            productActivities(skuId)
                        }
                    } else break@label
                }
            }
        }
    }

    private fun displayProducts(isEmptyProductsList: Boolean) {
        println("--------------------PRODUCTS----------------------")
        if(isEmptyProductsList) {
            println("       No products found        ")
        } else {
            var sno = 1
            for(productDetails in productsList) {
                println("${sno++}. ${productDetails.first.productName} - Rs.${productDetails.first.price} - ${productDetails.first.category.category} - ${productDetails.second.status}")
            }
        }
    }

    fun productActivities(skuId: String) {
        try {
            while (true) {
                productSku = productActivities.getProductDetails(skuId)
                displayProductDetails(productSku)
                val productActivitiesDashboard = ProductActivitiesDashboard.values()
                super.showDashboard("PRODUCT DASHBOARD", productActivitiesDashboard)
                when (super.getUserChoice(productActivitiesDashboard)) {
                    ProductActivitiesDashboard.ADD_TO_CART -> {
                        if(isLoggedIn) {
                            if(this.productSku.second == StockStatus.INSTOCK) {
                                if(cartActivities.addToCart(accountInfo.cartId, skuId)) {
                                    println("Product added to cart!")
                                } else {
                                    println("Can't add to cart!")
                                }
                            } else {
                                println("Product Out of Stock! Can't add to cart!")
                            }
                        } else {
                            println("Login to your account!")
                        }
                    }

                    ProductActivitiesDashboard.ADD_TO_WISHLIST -> {
                        if(isLoggedIn) {
                            if(wishListsActivities.addProductToWishList(accountInfo.wishListId, skuId)) {
                                println("Product added to wishlist!")
                            } else {
                                println("Product already added to wishlist!")
                            }
                        } else {
                            println("Login to your account!")
                        }
                    }

                    ProductActivitiesDashboard.REMOVE_FROM_WISHLIST -> {
                        if(isLoggedIn) {
                            if(wishListsActivities.removeProductFromWishList(accountInfo.wishListId, skuId)) {
                                println("Product removed from wishlist!")
                            } else {
                                println("Product not yet added to wishlist!")
                            }
                        } else {
                            println("Login to your account!")
                        }
                    }

                    ProductActivitiesDashboard.BUY_NOW -> {
                        if(isLoggedIn) {
                            if(this.productSku.second == StockStatus.INSTOCK) {
                                checkOutPage.initializer(addressPage, paymentPage, skuId, accountInfo)
                                checkOutPage.openCheckOutPage()
                            } else {
                                println("Product out of stock!")
                            }
                        } else {
                            println("Login to your account!")
                        }
                    }

                    ProductActivitiesDashboard.GO_BACK -> {
                        break
                    }
                }
            }
        } catch(exception: Exception) {
            println("Something went wrong!")
        }
    }
    private fun selectAProduct(): String { // returns skuId
        var option: Int
        var selectedProductId: String
        while(true){
            println("SELECT A PRODUCT: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, productsList.size)) {
                    selectedProductId = productsList[option - 1].first.skuId
                    break
                } else {
                    println("Invalid option! Try again!")
                }
            } catch(exception: Exception) {
                println("Enter valid option!")
            }
        }
        return selectedProductId
    }

    private fun displayProductDetails(productSku: Pair<ProductSku, StockStatus>) {
        println("---------------------------------------------")
        println("""PRODUCT NAME       : ${productSku.first.productName}
                  |PRODUCT PRICE      : Rs.${productSku.first.price}
        """.trimMargin())
        when(productSku.first) {
            is ProductSku.Book -> {
                val selectedProductSku = productSku.first as ProductSku.Book
                println("""CATEGORY           : ${selectedProductSku.category.category}
                          |BOOK TYPE          : ${selectedProductSku.bookType.type}
                          |PRODUCT STATUS     : ${productSku.second.status} 
                """.trimMargin())
            }
            is ProductSku.Mobile -> {
                val selectedProductSku = productSku.first as ProductSku.Mobile
                println("""CATEGORY           : ${selectedProductSku.category.category}
                          |BRAND              : ${selectedProductSku.brand.brandName}
                          |PRODUCT STATUS     : ${productSku.second.status}
                """.trimMargin())
            }
            is ProductSku.Clothing -> {
                val selectedProductSku = productSku.first as ProductSku.Clothing
                println("""CATEGORY           : ${selectedProductSku.category.category}
                          |COLOUR             : ${selectedProductSku.colour.colour}
                          |GENDER             : ${selectedProductSku.gender.gender}
                          |PRODUCT STATUS     : ${productSku.second.status}
                """.trimMargin())
            }
            is ProductSku.Earphone -> {
                val selectedProductSku = productSku.first as ProductSku.Earphone
                println("""CATEGORY           : ${selectedProductSku.category.category}
                          |BRAND              : ${selectedProductSku.brand.brandName}
                          |PRODUCT STATUS     : ${productSku.second.status}
                """.trimMargin())
            }
        }
    }

    private fun applyFilter(category: ProductCategory) {
        lateinit var filterArray: ArrayList<FilterBy>
        filterOption = when(category) {
            ProductCategory.BOOK -> {
                filterArray = arrayListOf()
                FilterBy.values().forEach {
                    if(it == FilterBy.PRICE || it == FilterBy.STATUS || it == FilterBy.BOOKTYPE) {
                        filterArray.add(it)
                    }
                }
                getFilterChoice("FILTERS FOR BOOK", filterArray.toTypedArray())
            }
            ProductCategory.MOBILE -> {
                filterArray = arrayListOf()
                FilterBy.values().forEach {
                    if(it == FilterBy.PRICE || it == FilterBy.STATUS || it == FilterBy.BRAND) {
                        filterArray.add(it)
                    }
                }
                getFilterChoice("FILTERS FOR MOBILE", filterArray.toTypedArray())
            }
            ProductCategory.CLOTHING -> {
                filterArray = arrayListOf()
                FilterBy.values().forEach {
                    if(it == FilterBy.PRICE || it == FilterBy.STATUS || it == FilterBy.GENDER || it == FilterBy.COLOUR) {
                        filterArray.add(it)
                    }
                }
                getFilterChoice("FILTERS FOR CLOTHING", filterArray.toTypedArray())
            }
            ProductCategory.EARPHONE -> {
                filterArray = arrayListOf()
                FilterBy.values().forEach {
                    if(it == FilterBy.PRICE || it == FilterBy.STATUS || it == FilterBy.BRAND) {
                        filterArray.add(it)
                    }
                }
                getFilterChoice("FILTERS FOR EARPHONE", filterArray.toTypedArray())
            }
        }
        if(Helper.confirm()) {
            filter = when(filterOption) {
                FilterBy.PRICE -> {
                    Filter.PriceFilter(getFilterChoice("PRICE", Price.values()))
                }

                FilterBy.STATUS -> {
                    Filter.StatusFilter(getFilterChoice("STATUS", StockStatus.values()))
                }

                FilterBy.BRAND -> {
                    Filter.BrandFilter(getFilterChoice("BRAND", Brand.values()))
                }

                FilterBy.BOOKTYPE -> {
                    Filter.BookTypeFilter(getFilterChoice("BOOK TYPE", BookType.values()))
                }

                FilterBy.GENDER -> {
                    Filter.GenderFilter(getFilterChoice("GENDER", Gender.values()))
                }

                FilterBy.COLOUR -> {
                    Filter.ColourFilter(getFilterChoice("COLOUR", Colour.values()))
                }
            }
            if(Helper.confirm()) {
                productsList = productActivities.getFilteredList(category, filterOption, filter)
            }
        }
    }

    private fun <E: Enum<E>> getFilterChoice(title: String, filters: Array<E>): E {
        super.showDashboard(title, filters)
        return super.getUserChoice(filters)
    }

}
package userInterface

import backend.CartActivities
import backend.ProductActivities
import backend.WishListsActivities
import data.AccountInfo
import data.Filters
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
    private lateinit var productsList: List<Pair<ProductSku, Filters.StatusFilters>>
    private lateinit var productSku: Pair<ProductSku, Filters.StatusFilters>
    private var isEmptyProductsList = false
    private lateinit var accountInfo: AccountInfo
    private var isLoggedIn = false
    private lateinit var filterOption: FilterOptions
    private lateinit var finalFilterOption: Any

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
                                val categories = ProductCategories.values()
                                lateinit var category: ProductCategories
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
                            if(this.productSku.second == Filters.StatusFilters.InStock()) {
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
                            if(this.productSku.second == Filters.StatusFilters.InStock()) {
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

    private fun displayProductDetails(product: Pair<ProductSku, Filters.StatusFilters>) {
        println("---------------------------------------------")
        println("""PRODUCT NAME       : ${product.first.productName}
                  |PRODUCT PRICE      : ${product.first.price}
        """.trimMargin())
        when(product.first) {
            is ProductSku.Book -> {
                val selectedProduct = product.first as ProductSku.Book
                println("""CATEGORY           : ${selectedProduct.category.category}
                          |BOOK TYPE          : ${selectedProduct.bookType.type}
                          |PRODUCT STATUS     : ${product.second.status} 
                """.trimMargin())
            }
            is ProductSku.Mobile -> {
                val selectedProduct = product.first as ProductSku.Mobile
                println("""CATEGORY           : ${selectedProduct.category.category}
                          |BRAND              : ${selectedProduct.brand.brandName}
                          |STORAGE            : ${selectedProduct.storage.storage}
                          |PRODUCT STATUS     : ${product.second.status}
                """.trimMargin())
            }
            is ProductSku.Clothing -> {
                val selectedProduct = product.first as ProductSku.Clothing
                println("""CATEGORY           : ${selectedProduct.category.category}
                          |COLOUR             : ${selectedProduct.colour.colour}
                          |GENDER             : ${selectedProduct.gender.gender}
                          |PRODUCT STATUS     : ${product.second.status}
                """.trimMargin())
            }
            is ProductSku.Earphone -> {
                val selectedProduct = product.first as ProductSku.Earphone
                println("""CATEGORY           : ${selectedProduct.category.category}
                          |EARPHONE TYPE      : ${selectedProduct.type.type}
                          |BRAND              : ${selectedProduct.brand.brandName}
                          |PRODUCT STATUS     : ${product.second.status}
                """.trimMargin())
            }
        }
    }

    private fun applyFilter(category: ProductCategories) {
        filterOption = when(category) {
            ProductCategories.BOOK -> {
                getFilterChoice("FILTERS FOR BOOK", FilterOptions.values())
            }
            ProductCategories.MOBILE -> {
                getFilterChoice("FILTERS FOR MOBILE", FilterOptions.values())
            }
            ProductCategories.CLOTHING -> {
                getFilterChoice("FILTERS FOR CLOTHING", FilterOptions.values())
            }
            ProductCategories.EARPHONE -> {
                getFilterChoice("FILTERS FOR EARPHONE", FilterOptions.values())
            }
        }
        if(Helper.confirm()) {
            finalFilterOption = when(filterOption) {
                FilterOptions.PRICE -> {
                    getFilterChoice("PRICE", PriceFilters.values())
                }
                FilterOptions.STATUS -> {
                    getFilterChoice("STATUS", StatusFilters.values())
                }
            }
            if(Helper.confirm()) {
                productsList = productActivities.getFilteredList(category, filterOption, finalFilterOption)
            }
        }
    }

    private fun <E: Enum<E>> getFilterChoice(title: String, filters: Array<E>): E {
        super.showDashboard(title, filters)
        return super.getUserChoice(filters)
    }

}
package userInterface

import backend.CartActivities
import backend.ProductActivities
import backend.WishListsActivities
import data.AccountInfo
import data.Product
import enums.ProductActivitiesDashboard
import enums.ProductStatus
import interfaces.DashboardServices
import utils.Helper
import java.util.*

class ShopPage(private val productActivities: ProductActivities) : DashboardServices {

    private lateinit var wishListsActivities: WishListsActivities
    private lateinit var cartActivities: CartActivities
    private lateinit var checkOutPage: CheckOutPage
    private lateinit var addressPage: AddressPage
    private lateinit var paymentPage: PaymentPage
    private lateinit var category: String
    private lateinit var productsList: Map<Int, Triple<String, String, Float>>
    private lateinit var product: Product
    private var isEmptyProductsList = false
    private lateinit var userId: String
    private lateinit var accountInfo: AccountInfo
    private var isLoggedIn = false


    fun initializer(
        userId: String,
        accountInfo: AccountInfo,
        wishListsActivities: WishListsActivities,
        cartActivities: CartActivities,
        checkOutPage: CheckOutPage,
        addressPage: AddressPage,
        paymentPage: PaymentPage
    ) {

        this.userId = userId
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

        while(true) {
            displayCategories()
            if(Helper.confirm()) {
                label@while(true) {
                    category = selectACategory()
                    if(Helper.confirm()) {
                        while(true) {
                            productsList = productActivities.getProductsList(category)
                            isEmptyProductsList = productsList.isEmpty()
                            if(isEmptyProductsList) {
                                displayProducts(true)
                                break
                            } else {
                                    while(true) {
                                        displayProducts(false)
                                        if(Helper.confirm()) {
                                            val productId = selectAProduct()
                                            if(Helper.confirm()) {
                                                productActivities(category, productId)
                                            } else break
                                        } else break@label
                                    }
                            }
                        }
                    } else {
                        break
                    }
                }
            } else {
                break
            }
        }
    }

    private fun displayCategories() {

        println("--------------CATEGORIES---------------")
        productActivities.getCategories().forEachIndexed{ index, category ->
            println("${index + 1}. ${category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}")
        }
    }

    private fun selectACategory(): String { // returns category name in lowercase

        var option: Int
        var selectedCategory: String
        while(true) {
            println("SELECT A CATEGORY: ")
            try {
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, productActivities.getCategories().size)) {
                    selectedCategory = productActivities.getCategories()[option - 1].lowercase()
                    break
                } else {
                    println("Invalid option! Try again!")
                }
            } catch(exception: Exception) {
                println("""Class: ShopPage: selectACategory(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedCategory
    }

    private fun displayProducts(isEmptyProductsList: Boolean) {
        if(isEmptyProductsList) {
            println("       No products found        ")
        } else {
            println("---------------${category.uppercase()}S---------------")
            for((id, product) in productsList) {
                println("${id}. ${product.second} - ${product.third}")
            }
        }
    }

    fun productActivities(category: String, productId: String) {

        productActivities.getProductsList(category)
        product = productActivities.getAProduct(productId)!!
        label@while(true) {
                displayProductDetails(product)
                val productActivitiesDashboard = ProductActivitiesDashboard.values()
                while (true) {
                    super.showDashboard("PRODUCT DASHBOARD", productActivitiesDashboard)
                    when (super.getUserChoice(productActivitiesDashboard)) {
                        ProductActivitiesDashboard.ADD_TO_CART -> {
                            if(isLoggedIn) {
                                if(cartActivities.addToCart(accountInfo.cartId, category, productId)) {
                                    println("Product added to cart!")
                                } else {
                                    println("Can't add to cart!")
                                }
                            } else {
                                println("Login to your account!")
                            }
                        }

                        ProductActivitiesDashboard.ADD_TO_WISHLIST -> {
                            if(isLoggedIn) {
                                if(wishListsActivities.addProductToWishList(accountInfo.wishListId, category, productId)) {
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
                                if(wishListsActivities.removeProductFromWishList(accountInfo.wishListId, productId)) {
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
                                if(product.status == ProductStatus.IN_STOCK) {
                                    checkOutPage.initializer(addressPage, paymentPage, productId, category, accountInfo)
                                    checkOutPage.openCheckOutPage()
                                } else {
                                    println("Product out of stock!")
                                }
                            } else {
                                println("Login to your account!")
                            }
                        }

                        ProductActivitiesDashboard.GO_BACK -> {
                            break@label
                        }
                    }
                }
        }
    }
    private fun selectAProduct(): String { // returns productId

        var option: Int
        var selectedProductId: String
        while(true){
            println("SELECT A PRODUCT: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, productActivities.getProductsList(category).size)) {
                    selectedProductId = productsList[option]!!.first
                    break
                } else {
                    println("Invalid option! Try again!")
                }
            } catch(exception: Exception) {
                println("""Class: ShopPage: selectAProduct(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedProductId
    }

    private fun displayProductDetails(product: Product) {

        println("---------------------------------------------")
        println("""PRODUCT NAME       : ${product.productName}
                  |PRODUCT PRICE      : ${product.price}
                  |PRODUCT STATUS     : ${product.status}
        """.trimMargin())
        when(product) { // Smart cast to 'Product.Book' is impossible, because 'product' is a mutable property that could have been changed by this time
            is Product.Book -> {
                println("""BOOK TYPE          : ${product.bookType}
                """.trimMargin())
            }
            is Product.Mobile -> {
                println("""BRAND              : ${product.brand}
                          |STORAGE            : ${product.storage}
                """.trimMargin())
            }
            is Product.Clothing -> {
                println("""COLOUR             : ${product.colour}
                          |GENDER             : ${product.gender}
                """.trimMargin())
            }
            is Product.Earphone -> {
                println("""EARPHONE TYPE      : ${product.type}
                          |BRAND              : ${product.brand}
                          |STORAGE            : ${product.colour}
                """.trimMargin())
            }
        }
    }

}
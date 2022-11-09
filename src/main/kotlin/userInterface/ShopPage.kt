package userInterface

import backend.CartActivities
import backend.ProductActivities
import backend.UserAccountActivities
import backend.WishListsActivities
import data.AccountInfo
import data.Product
import enums.ProductActivitiesDashboard
import enums.ProductStatus
import interfaces.DashboardServices
import utils.Helper
import java.util.*

class ShopPage(): DashboardServices {

    private val userAccountActivities by lazy { UserAccountActivities()}
    private val productActivities = ProductActivities()
    private val wishListsActivities by lazy { WishListsActivities() }
    private val cartActivities by lazy { CartActivities() }
    private lateinit var category: String
    private lateinit var productsList: Map<Int, Triple<String, String, Float>>
    private lateinit var product: Product
    private var isEmptyProductsList = false
    private lateinit var userId: String
    private lateinit var accountInfo: AccountInfo

    constructor(userId: String, accountInfo: AccountInfo): this() {
        this.userId = userId
        this.accountInfo = accountInfo
    }

    fun setUserIdAndAccountInfo(userId: String) {
        this.userId = userId
        accountInfo = userAccountActivities.getAccountInfo(userId)!!
    }

    fun openShopPage() {

        while(true) {
            displayCategories()
            if(Helper.confirm()) {
                label@while(true) {
                    category = selectACategory()
                    if(Helper.confirm()) {
                        while(true) {
                            isEmptyProductsList = displayProducts(category)
                            if(isEmptyProductsList) {
                                break
                            } else {
                                if(Helper.confirm()) {
                                    productActivities()
                                } else {
                                    break@label
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
        println("---------------Categories----------------")
        productActivities.getCategories().forEachIndexed{ index, category ->
            println("${index + 1}. ${category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}")
        }
    }

    private fun selectACategory(): String { // returns category name in lowercase
        var option: Int
        var selectedCategory: String
        while(true){
            println("Select a category: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, productActivities.getCategories().size)) {
                    selectedCategory = productActivities.getCategories()[option - 1].lowercase()
                    break
                } else {
                    println("Invalid option! Try again!")
                }
            } catch(exception: Exception) {
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedCategory
    }

    private fun displayProducts(category: String): Boolean {
        println("------------------${category}s--------------------")
        productsList = productActivities.getProductsList(category)
        return if(productsList.isEmpty()) {
            println("       No products found        ")
            true
        } else {
            for((id, product) in productsList) {
                println("${id}. ${product.second} - ${product.third}")
            }
            false
        }
    }

    private fun productActivities() {

        label@while(true) {
            val productId = selectAProduct()
            if(Helper.confirm()) {
                displayProductDetails(productId)
                val productActivitiesDashboard = ProductActivitiesDashboard.values()
                while (true) {
                    super.showDashboard("Product Dashboard", productActivitiesDashboard)
                    when (super.getUserChoice(productActivitiesDashboard)) {
                        ProductActivitiesDashboard.ADD_TO_CART -> {
                            if(::userId.isInitialized) {
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
                            if(::userId.isInitialized) {
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
                            if(::userId.isInitialized) {
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
                            if(::userId.isInitialized) {
                                if(product.status == ProductStatus.IN_STOCK) {
                                    val checkOutPage = CheckOutPage(userId, productId, category)
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
            } else {
                break
            }
        }
    }
    private fun selectAProduct(): String { // returns productId
        var option: Int
        var selectedProductId: String
        while(true){
            println("Select a product: ")
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
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedProductId
    }

    private fun displayProductDetails(productId: String) {
        product = productActivities.getAProduct(productId)!!
        println("""Product name       : ${product.productName}
                  |Product price      : ${product.price}
        """.trimMargin())
        when(product) { // Smart cast to 'Product.Book' is impossible, because 'product' is a mutable property that could have been changed by this time
            is Product.Book -> {
                val book = product as Product.Book
                println("""Book type          : ${book.bookType}
                """.trimMargin())
            }
            is Product.Mobile -> {
                val mobile = product as Product.Mobile
                println("""Brand              : ${mobile.brand}
                          |Storage            : ${mobile.storage}
                """.trimMargin())
            }
            is Product.Clothing -> {
                val clothing = product as Product.Clothing
                println("""Colour             : ${clothing.colour}
                          |Gender             : ${clothing.gender}
                """.trimMargin())
            }
            is Product.Earphone -> {
                val earphone = product as Product.Earphone
                println("""Earphone type      : ${earphone.type}
                          |Storage            : ${earphone.colour}
                """.trimMargin())
            }

        }
    }

}
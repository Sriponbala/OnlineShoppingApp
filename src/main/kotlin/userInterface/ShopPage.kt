package userInterface

import backend.CartActivities
import backend.ShopActivities
import data.Product
import enums.ProductActivitiesDashboard
import utils.Helper

class ShopPage {

    private val shopActivities = ShopActivities()
    private lateinit var category: String
    private lateinit var productsList: Map<Int, Triple<String, String, Float>>
    private lateinit var product: Product
    private var isEmptyProductsList = false
    private lateinit var userId: String

    fun setUserId(userId: String) {
        this.userId = userId
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
        shopActivities.getCategories().forEachIndexed{ index, category ->
            println("${index + 1}. ${category.capitalize()}")
        }
    }

    private fun selectACategory(): String {
        var option: Int
        var selectedCategory: String
        while(true){
            println("Select a category: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, shopActivities.getCategories().size)) {
                    selectedCategory = shopActivities.getCategories()[option - 1].lowercase()
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
        productsList = shopActivities.getProductsList(category)
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

    fun productActivities() {

        label@while(true) {
            val productId = selectAProduct()
            if(Helper.confirm()) {
                displayProductDetails(productId)
                val productActivitiesDashboard = ProductActivitiesDashboard.values()
                while (true) {
                    showDashboard("Product Dashboard", productActivitiesDashboard)
                    when (getUserChoice(productActivitiesDashboard)) {
                        ProductActivitiesDashboard.ADD_TO_CART -> {

                            if(CartActivities().addToCart(userId, category, productId)) {
                                println("Product added to cart!")
                            } else {
                                println("Can't add to cart, product is out of stock!")
                            }
                        }

                        ProductActivitiesDashboard.ADD_TO_WISHLIST -> {
                            if(::userId.isInitialized) {
                                if(shopActivities.addProductToWishList(userId, category, productId)) {
                                    println("Product added to wishlist!")
                                } else {
                                    println("Product already added to wishlist!")
                                }
                            } else {
                                println("Login to your account!")
                            }
                        }

                        ProductActivitiesDashboard.REMOVE_FROM_WISHLIST -> {
                            if(shopActivities.removeProductFromWishList(userId, productId)) {
                                println("Product removed from wishlist!")
                            } else {
                                println("Product not yet added to wishlist!")
                            }
                        }

                        ProductActivitiesDashboard.BUY_NOW -> {

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
    private fun selectAProduct(): String {
        var option: Int
        var selectedProductId: String
        while(true){
            println("Select a product: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, shopActivities.getProductsList(category).size)) {
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
        product = shopActivities.getAProduct(productId)!!
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

    private fun <E: Enum<E>> showDashboard(title: String, enumArray: Array<E>) {

        println("-------------${title.uppercase()}-------------")
        for(element in enumArray) {
            println("${element.ordinal+1}. $element")
        }
    }
    private fun <E: Enum<E>> getUserChoice(enumArray: Array<E>): E {

        while (true) {
            try {
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
                    return enumArray[dashBoardOption-1]
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class AddressPage: getUserChoice(): Exception: $exception")
            }
        }
    }

}
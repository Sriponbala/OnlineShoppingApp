package userInterface

import backend.WishListsActivities
import data.Product
import data.WishList
import enums.WishListDashboard
import interfaces.DashboardServices
import utils.Helper

class WishListPage(private val wishListsActivities: WishListsActivities): DashboardServices {

    private lateinit var wishListProducts : ArrayList<Product>
    private var isEmptyWishList: Boolean = true
    private lateinit var wishListId: String
    private lateinit var shopPage: ShopPage

    fun initializer(wishListId: String, shopPage: ShopPage) {
        this.wishListId = wishListId
        this.shopPage = shopPage
    }

    fun openWishListPage() {

        while(true) {
            wishListProducts = wishListsActivities.getWishListProducts(wishListId)
            checkIfWishListIsEmpty(wishListProducts)
            displayWishListProducts(isEmptyWishList)
            if(Helper.confirm()) {
                val selectedProduct: String
                if(!isEmptyWishList) {
                    selectedProduct = selectAProduct()
                    val wishListDashboard = WishListDashboard.values()
                    while(true) {
                        super.showDashboard("WishList Dashboard", wishListDashboard)
                        when(super.getUserChoice(wishListDashboard)) {
                            WishListDashboard.VIEW_PRODUCT -> {
                                println(selectedProduct)
                                shopPage.productActivities(selectedProduct)
                                break
                            }
                            WishListDashboard.DELETE_PRODUCT -> {
                                wishListsActivities.removeProductFromWishList(wishListId, selectedProduct)
                                break
                            }
                            WishListDashboard.GO_BACK -> {
                                break
                            }
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
        var selectedProduct = ""
        while(true){
            println("SELECT A PRODUCT: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option,wishListProducts.size)) {
                   selectedProduct = wishListProducts.let{
                       it[option - 1].productId
                   }
                }
                break
            } catch(exception: Exception) {
                println("""Class: WistListPage: selectAProduct(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedProduct
    }

    private fun displayWishListProducts(isEmptyWishList: Boolean) {
        println("-------------------${WishList.wishListName}----------------------")
        if(isEmptyWishList) {
            println("        No items found        ")
        } else {
            wishListProducts.forEachIndexed { index, product ->
                println("${index + 1}. ${product.productName} - ${product.price}")
            }
        }
    }

    private fun checkIfWishListIsEmpty(wishListProducts: ArrayList<Product>?) {
        isEmptyWishList = wishListProducts?.isEmpty() == true
    }
}
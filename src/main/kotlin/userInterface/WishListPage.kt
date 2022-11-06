package userInterface

import backend.WishListsActivities
import data.Product
import data.WishList
import enums.WishListDashboard
import utils.Helper

class WishListPage {

    private val wishListsActivities = WishListsActivities()
    private var wishListProducts : ArrayList<Product>? = null
    private var isEmptyWishList: Boolean = true


    fun openWishListPage(wishListId: String) {

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
                        showDashboard("WishList Dashboard", wishListDashboard)
                        when(getUserChoice(wishListDashboard)) {
                            WishListDashboard.VIEW_PRODUCT -> {
                                println(selectedProduct)
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
            println("Select a product: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option,wishListProducts?.size!!)) {
                   selectedProduct = wishListProducts?.let{
                       it[option - 1].productId
                   } ?: ""
                }
                break
            } catch(exception: Exception) {
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
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
            wishListProducts?.forEachIndexed { index, product ->
                println("${index + 1}. ${product.productName} - ${product.price}")
            }
        }
    }

    private fun checkIfWishListIsEmpty(wishListProducts: ArrayList<Product>?) {
        isEmptyWishList = wishListProducts?.isEmpty() == true
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
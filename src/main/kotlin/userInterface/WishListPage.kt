package userInterface

import backend.WishListsActivities
import data.WishList
import enums.WishListDashboard
import utils.Helper

class WishListPage(private val userId: String) {

    private val wishListsActivities = WishListsActivities(this.userId)
    private var wishListProducts = wishListsActivities.getWishListProducts()
    private var isEmptyWishList = true
    fun openWishListPage() {

        displayWishListProducts()
        val selectedProduct: String
        if(!isEmptyWishList) {
            selectedProduct = selectAProduct()
            val wishListDashboard = WishListDashboard.values()
            while(true) {
                showDashboard("WishList Dashboard", wishListDashboard)
                when(getUserChoice(wishListDashboard)) {
                    WishListDashboard.VIEW_PRODUCT -> {
                        println("      ${WishList.wishListName}      ")
                        println(selectedProduct)
                    }
                    WishListDashboard.DELETE_PRODUCT -> {
                        wishListsActivities.deleteProductFromWishList(selectedProduct)

                    }
                    WishListDashboard.GO_BACK -> {
                        break
                    }
                }
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

    private fun displayWishListProducts() {
        isEmptyWishList = if(wishListsActivities.getWishListProducts()?.isEmpty() == true) {
            println("        Empty Wishlist        ")
            true
        } else {
            wishListsActivities.getWishListProducts()?.forEachIndexed { index, product ->
                println("${index + 1}. ${product.productName} - ${product.price}")
            }
            false
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
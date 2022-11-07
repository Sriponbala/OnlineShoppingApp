package userInterface

import backend.CartActivities
import data.Item
import enums.CartActivitiesDashboard
import enums.ProductQuantityManagement
import utils.Helper

class CartPage {

    private val cartActivities = CartActivities()
    private lateinit var cartId: String
    private lateinit var userId: String
    private var isCartEmpty = false
    private lateinit var cartItems: List<Item>


    fun setUserIdAndCartId(userId: String) {
        this.userId = userId
        this.cartId = cartActivities.getCartId(userId)
    }

    fun openCartPage() {

        while(true) {
            isCartEmpty = checkIfCartIsEmpty(cartId)
            if(!isCartEmpty) {
                cartItems = cartActivities.getCartItems(cartId)
                displayCartItems()
                val cartActivitiesDashboard = CartActivitiesDashboard.values()
                showDashboard("Cart Dashboard", cartActivitiesDashboard)
                when(getUserChoice(cartActivitiesDashboard)) {
                    CartActivitiesDashboard.SELECT_A_PRODUCT -> {
                        val item = selectAnItem()
                        doActivitiesOnSelectedItem(item)
                    }
                    CartActivitiesDashboard.PROCEED_TO_BUY -> {
                        val buyNowPage = BuyNowPage(userId, cartItems)
                        buyNowPage.openBuyNowPage()
                    }
                    CartActivitiesDashboard.GO_BACK -> {
                        break
                    }
                }
            } else {
                println("No items found in cart!")
                break
            }
        }
    }

    private fun displayCartItems() {
        cartItems.forEachIndexed { index, item ->
            println("${index + 1}. $item")
        }
    }

    private fun doActivitiesOnSelectedItem(item: Item) {
        val productQuantityManagement = ProductQuantityManagement.values()
        showDashboard("Activities on selected product", productQuantityManagement)
        while(true) {
            when(getUserChoice(productQuantityManagement)) {
                ProductQuantityManagement.CHANGE_QUANTITY -> {

                }
                ProductQuantityManagement.REMOVE -> {
                    if(cartActivities.removeFromCart(cartId, item.productId, true)){
                        println("Item removed from cart!")
                        break
                    } else {
                        println("Failed removing item from cart")
                        break
                    }
                }
                ProductQuantityManagement.GO_BACK -> {
                    break
                }
            }
        }
    }

    private fun selectAnItem(): Item {
        var option: Int
        var selectedItem: Item
        while(true){
            println("Select an item: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, cartItems.size)) {
                    println("Selected address: ${cartItems[option - 1]}")
                    selectedItem = cartItems[option - 1]
                    break
                } else {
                    println("Invalid option! Try again")
                }
            } catch(exception: Exception) {
                println("""Class: AddressPage: selectAnAddress(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedItem
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
                println("Class BuyNowPage: getUserChoice(): Exception: $exception")
            }
        }
    }

    private fun checkIfCartIsEmpty(cartId: String): Boolean {
        return cartActivities.getCartItems(cartId).isEmpty()
    }
}
package userInterface

import backend.CartActivities
import data.Item
import enums.CartActivitiesDashboard
import enums.ProductQuantityManagement
import enums.ProductStatus
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
                        val items = mutableListOf<Item>()
                        for(cartItem in cartItems) {
                            if(cartItem.status != ProductStatus.OUT_OF_STOCK) {
                                items.add(cartItem)
                            }
                        }
                        val checkOutPage = CheckOutPage(userId, items)
                        checkOutPage.openCheckOutPage()
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
        println("Subtotal: ${cartActivities.calculateAndUpdateSubtotal(cartId, cartItems)}")
    }

    private fun doActivitiesOnSelectedItem(item: Item) {
        val productQuantityManagement = ProductQuantityManagement.values()
        while(true) {
            showDashboard("Activities on selected product", productQuantityManagement)
            when(getUserChoice(productQuantityManagement)) {
                ProductQuantityManagement.CHANGE_QUANTITY -> {
                    if(item.status == ProductStatus.IN_STOCK) {
                        val quantity = getQuantity(item.productId, item.category)
                        if(Helper.confirm()) {
                            if(cartActivities.changeQuantityAndUpdateTotalPriceOfItem(cartId, item, quantity)) {
                                println("Quantity changed!")
                            } else {
                                println("Failed to change quantity")
                            }
                        }
                    } else {
                        println("Product out of stock!")
                    }

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
                println("Enter your choice: ")
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
                    return enumArray[dashBoardOption-1]
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("Class CartPage: getUserChoice(): Exception: $exception")
            }
        }
    }

    private fun checkIfCartIsEmpty(cartId: String): Boolean {
        return cartActivities.getCartItems(cartId).isEmpty()
    }

    fun getQuantity(productId: String, category: String): Int {
        var quantity: Int = 1
        while(true) {
            if(Helper.confirm()) {
                println("Enter the quantity required: ")
                try {
                    val input = readLine()!!.toInt()
                    if(input in 1..4) {
                        val availableQuantity = cartActivities.getAvailableQuantityOfProduct(productId, category)
                        if(availableQuantity >= input) {
                            quantity = input
                            break
                        } else {
                            println("Only $availableQuantity items available!")
                        }
                    } else {
                        println("You can select a maximum of 4 items!")
                    }
                } catch(exception: Exception) {
                    println("Class CartPage(): getQuantity(): Exception: $exception")
                }
            } else break
        }
        return quantity
    }
}
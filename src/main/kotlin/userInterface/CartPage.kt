package userInterface

import backend.CartActivities
import data.AccountInfo
import data.Item
import enums.CartActivitiesDashboard
import enums.ProductQuantityManagement
import enums.ProductStatus
import interfaces.DashboardServices
import utils.Helper

class CartPage(private val cartActivities: CartActivities): DashboardServices {

    private var isCartEmpty = false
    private lateinit var cartItems: List<Item>
    private lateinit var cartId: String

    fun initializer(cartId: String) {

        this.cartId = cartId
    }

    fun openCartPage(addressPage: AddressPage, paymentPage: PaymentPage, checkOutPage: CheckOutPage, accountInfo: AccountInfo) {

        while(true) {
            isCartEmpty = checkIfCartIsEmpty(cartId)
            if(!isCartEmpty) {
                cartItems = cartActivities.getCartItems(cartId)
                displayCartItems()
                val cartActivitiesDashboard = CartActivitiesDashboard.values()
                super.showDashboard("CART DASHBOARD", cartActivitiesDashboard)
                when(super.getUserChoice(cartActivitiesDashboard)) {
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
                        checkOutPage.initializer(addressPage, paymentPage, items, accountInfo)
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
            println("""${index + 1}. Item Name        : ${item.productName}
                |   Item price       : ${item.productPrice}
                |   Quantity         : ${item.quantity}
                |   Total Price      : ${item.totalPrice}
                |   Status           : ${item.status}
            """.trimMargin())
        }
        println("   Subtotal: ${cartActivities.calculateAndUpdateSubtotal(cartId, cartItems)}")
    }

    private fun doActivitiesOnSelectedItem(item: Item) {

        val productQuantityManagement = ProductQuantityManagement.values()
        while(true) {
            super.showDashboard("ACTIVITIES ON SELECTED PRODUCT", productQuantityManagement)
            when(super.getUserChoice(productQuantityManagement)) {
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
            println("SELECT AN ITEM: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, cartItems.size)) {
                    println("SELECTED ITEM: ${cartItems[option - 1]}")
                    selectedItem = cartItems[option - 1]
                    break
                } else {
                    println("Invalid option! Try again")
                }
            } catch(exception: Exception) {
                println("""Class: CartPage: selectAnItem(): Exception: $exception
                    |Enter again!
                """.trimMargin())
            }
        }
        return selectedItem
    }

    private fun checkIfCartIsEmpty(cartId: String): Boolean {

        return cartActivities.getCartItems(cartId).isEmpty()
    }

    private fun getQuantity(productId: String, category: String): Int {

        var quantity = 1
        while(true) {
            if(Helper.confirm()) {
                println("ENTER THE QUANTITY REQUIRED: ")
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
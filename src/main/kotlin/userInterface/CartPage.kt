package userInterface

import backend.CartActivities
import data.*
import enums.CartActivitiesDashboard
import enums.ProductQuantityManagement
import enums.StockStatus
import interfaces.DashboardServices
import utils.Helper

class CartPage(private val cartActivities: CartActivities): DashboardServices {

    private var isCartEmpty = false
    private lateinit var cartItems: MutableList<Triple<CartItem, ProductSku, StockStatus>>
    private lateinit var cartId: String

    fun initializer(cartId: String) {
        this.cartId = cartId
    }

    fun openCartPage(addressPage: AddressPage, paymentPage: PaymentPage, checkOutPage: CheckOutPage, accountInfo: AccountInfo) {
        while(true) {
            isCartEmpty = cartActivities.checkIfCartIsEmpty(cartId)
            if(!isCartEmpty) {
                cartItems = cartActivities.getCartItems(cartId)
                displayCartItems()
                val cartActivitiesDashboard = CartActivitiesDashboard.values()
                super.showDashboard("CART DASHBOARD", cartActivitiesDashboard)
                when(super.getUserChoice(cartActivitiesDashboard)) {
                    CartActivitiesDashboard.SELECT_A_PRODUCT -> {
                        val cartItem = selectACartItem()
                        doActivitiesOnSelectedItem(cartItem)
                    }
                    CartActivitiesDashboard.PROCEED_TO_BUY -> {
                        val items = mutableListOf<Triple<CartItem, ProductSku, StockStatus>>()
                        for(cartItem in cartItems) {
                            if(cartItem.third != StockStatus.OUTOFSTOCK) {
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
        cartItems.forEachIndexed { index, cartItem ->
            println("""${index + 1}. Item Name        : ${cartItem.second.productName}
                |   Item price       : ${cartItem.second.price}
                |   Quantity         : ${cartItem.first.quantity}
                |   Status           : ${cartItem.third.status}
            """.trimMargin())
        }
        println("   Subtotal: ${cartActivities.calculateAndUpdateSubtotal(cartId, cartItems)}")
    }

    private fun doActivitiesOnSelectedItem(cartItem: Triple<CartItem, ProductSku, StockStatus>) {
        val productQuantityManagement = ProductQuantityManagement.values()
        while(true) {
            super.showDashboard("ACTIVITIES ON SELECTED PRODUCT", productQuantityManagement)
            when(super.getUserChoice(productQuantityManagement)) {
                ProductQuantityManagement.CHANGE_QUANTITY -> {
                    if(cartItem.third == StockStatus.INSTOCK) {
                        val quantity = getQuantity(cartItem.first.skuId)
                        if(Helper.confirm()) {
                            if(cartActivities.changeQuantityOfCartItem(cartId, cartItem.first.skuId, quantity)) {
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
                    if(cartActivities.removeFromCart(cartId, cartItem.first.skuId)){
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

    private fun selectACartItem(): Triple<CartItem, ProductSku, StockStatus> {
        var option: Int
        var selectedItem: Triple<CartItem, ProductSku, StockStatus>
        while(true){
            println("SELECT AN ITEM: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, cartItems.size)) {
                    selectedItem = cartItems[option - 1]
                    break
                } else {
                    println("Invalid option! Try again")
                }
            } catch(exception: Exception) {
                println("Enter valid option!")
            }
        }
        return selectedItem
    }

    private fun getQuantity(skuId: String): Int {
        var quantity = 1
        while(true) {
            if(Helper.confirm()) {
                println("ENTER THE QUANTITY REQUIRED: ")
                try {
                    val input = readLine()!!.toInt()
                    if(input in 1..4) {
                        val availableQuantity = cartActivities.getAvailableQuantityOfProduct(skuId)
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
                    println("Enter valid value!")
                }
            } else break
        }
        return quantity
    }
}
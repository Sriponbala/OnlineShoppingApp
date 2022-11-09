package userInterface

import backend.CheckOutActivities
import backend.UserAccountActivities
import data.AccountInfo
import data.Item
import data.Order
import enums.CheckOutPageDashboard
import enums.ProductQuantityManagement
import interfaces.DashboardServices
import utils.Helper

class CheckOutPage(): DashboardServices {

    private var checkOutActivities: CheckOutActivities = CheckOutActivities()
    private var finalizedListOfItems = arrayListOf<Item>()
    private lateinit var userId: String
    private lateinit var shippingAddress: String
    private var modeOfPayment: String? = null
    private lateinit var accountInfo: AccountInfo
    private lateinit var item: Item
    private lateinit var items: ArrayList<Item>
    private lateinit var orders: ArrayList<Order>
    private var totalBill: Float = 0f

    constructor(userId: String, productId: String, category: String): this() { // directly from ShopPage
        this.userId = userId
        accountInfo = UserAccountActivities().getAccountInfo(userId)!!
        this.item = checkOutActivities.createItemToBuy(productId, category)
        finalizedListOfItems.add(this.item)
    }

    constructor(userId: String, items: List<Item>): this() { // from cart
        this.userId = userId
        accountInfo = UserAccountActivities().getAccountInfo(userId)!!
        this.items = items as ArrayList<Item>
        this.finalizedListOfItems = this.items
    }


    fun openCheckOutPage() {

        while(true) {
            displayItemDetails(finalizedListOfItems)
            val checkOutPageDashboard = CheckOutPageDashboard.values()
            super.showDashboard("Check out page", checkOutPageDashboard)
            when(super.getUserChoice(checkOutPageDashboard)) {
                CheckOutPageDashboard.SELECT_A_PRODUCT -> {
                    if(finalizedListOfItems.isNotEmpty()) {
                        val item = selectAnItem()
                        doActivitiesOnSelectedItem(item)
                    } else {
                        println("No items selected to buy!")
                    }
                }
                CheckOutPageDashboard.PROCEED_TO_BUY -> {
                    if(finalizedListOfItems.isNotEmpty()) {
                        proceedToBuy()
                        break
                    } else {
                        println("No items selected to buy!")
                    }

                }
                CheckOutPageDashboard.GO_BACK -> break
            }
        }
    }

    private fun proceedToBuy() {
        try {

            val addressPage = AddressPage(userId)
            label@while(true) {
                println("Select an address: ")
                addressPage.setSelectAddress(true)
                shippingAddress = addressPage.selectAddressForDelivery()
                if(shippingAddress != "") {
                    if(Helper.confirm()) {
                        val paymentPage = PaymentPage()
                        while(true) {
                            println("Select mode of payment: ")
                            modeOfPayment = paymentPage.selectModeOfPayment()
                            if(Helper.confirm()) {
                                if(modeOfPayment != null) {
                                    println("Do you want to place order?")
                                    if (Helper.confirm()) {
                                        totalBill = checkOutActivities.getTotalBill(finalizedListOfItems)
                                        checkOutActivities.updateAvailableQuantityAndStatusOfProducts(finalizedListOfItems)
                                        checkOutActivities.createOrders(finalizedListOfItems, shippingAddress)
                                        orders = checkOutActivities.getOrders()
                                        displayOrdersSummary(orders, totalBill)
                                        if(checkOutActivities.addOrdersToOrdersHistory(
                                                accountInfo.ordersHistoryId, orders
                                            )) {
                                            println("Orders added to orders  history!")
                                        } else {
                                            println("Orders not added to orders history!")
                                        }
                                        checkOutActivities.clearOrders()
                                        if(::item.isInitialized) {
                                            if (checkOutActivities.removeFromCart(
                                                    accountInfo.cartId,
                                                    item.productId,
                                                    true
                                                )
                                            ) {
                                                println("Ordered placed! Item removed from cart")
                                            } else {
                                                println("Order placed!")
                                            }
                                        } else if (::items.isInitialized) {
                                            if (checkOutActivities.clearCartItems(accountInfo.cartId, items, true)) {
                                                println("Orders placed! Items removed from cart")
                                            }
                                        }
                                        checkOutActivities.updateAvailableQuantityAndStatusOfCartItems()
                                        break@label
                                    }
                                }
                            } else {
                                break
                            }
                        }
                    } else {
                        break
                    }
                } else {
                    break
                }
            }
        } catch(exception: Exception) {
            println("Class CheckOutPage: showDashBoard(): Exception: ${exception.message}")
        }
    }

    private fun displayOrdersSummary(orders: ArrayList<Order>, totalBill: Float) {
        println("---------------Orders Summary------------------")
        orders.forEachIndexed { index, order ->
            println("${index + 1}. Item Name        : ${order.item.productName}\n" +
                    "   Item price       : ${order.item.productPrice}\n" +
                    "   Quantity         : ${order.item.quantity}\n" +
                    "   Total Price      : ${order.item.totalPrice}\n" +
                    "   Status           : ${order.item.status} " +
                    "   Shipping Address : $shippingAddress\n" +
                    "   Ordered Date     : ${order.orderedDate}\n" +
                    "   Delivery Date    : ${order.deliveryDate}")
        }
            println("   Total bill paid  : $totalBill")
    }

    private fun displayItemDetails(items: MutableList<Item>) {
        items.forEachIndexed { index, item ->
            println("${index + 1}. Item Name        : ${item.productName}\n" +
                    "   Item price       : ${item.productPrice}\n" +
                    "   Quantity         : ${item.quantity}\n" +
                    "   Total Price      : ${item.totalPrice}\n" +
                    "   Status           : ${item.status}")
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
                if(Helper.checkValidRecord(option, finalizedListOfItems.size)) {
                    println("Selected address: ${finalizedListOfItems[option - 1]}")
                    selectedItem = finalizedListOfItems[option - 1]
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

    private fun getQuantity(productId: String, category: String): Int {
        var quantity = 1
        while(true) {
            if(Helper.confirm()) {
                println("Enter the quantity required: ")
                try {
                    val input = readLine()!!.toInt()
                    if(input in 1..4) {
                        val availableQuantity = checkOutActivities.getAvailableQuantityOfProduct(productId, category)
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
                    println("Class CheckOutPage(): getQuantity(): Exception: $exception")
                }
            } else break
        }
        return quantity
    }

    private fun doActivitiesOnSelectedItem(item: Item) {
        val productQuantityManagement = ProductQuantityManagement.values()
        while(true) {
            super.showDashboard("Activities on selected product", productQuantityManagement)
            when(super.getUserChoice(productQuantityManagement)) {
                ProductQuantityManagement.CHANGE_QUANTITY -> {
                    val quantity = getQuantity(item.productId, item.category)
                    if(Helper.confirm()) {
                        checkOutActivities.changeQuantityAndUpdateTotalPriceOfItem(item, quantity)
                    }
                }
                ProductQuantityManagement.REMOVE -> {
                        finalizedListOfItems.remove(item)
                        println("Item removed from finalised items!")
                        break
                }
                ProductQuantityManagement.GO_BACK -> {
                    break
                }
            }
        }
    }

}
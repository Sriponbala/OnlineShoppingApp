package userInterface

import backend.CartActivities
import backend.UserAccountActivities
import data.AccountInfo
import data.Item
import enums.BuyNowPageDashboard
import utils.Helper

class BuyNowPage() {

    private var finalizedListOfItems = mutableListOf<Item>()
    private lateinit var userId: String
   // private  lateinit var ordersHistoryId: String
    private lateinit var shippingAddress: String
    private var modeOfPayment: String? = null
    private var orderedDate = ""
    private var deliveryDate =  ""
    private lateinit var accountInfo: AccountInfo
    private lateinit var item: Item
    private lateinit var items: MutableList<Item>
    private val cartActivities = CartActivities()

    constructor(userId: String, item: Item): this() {
        this.userId = userId
        //this.ordersHistoryId = ordersHistoryId
        accountInfo = UserAccountActivities().getAccountInfo(userId)!!
        this.item = item
        finalizedListOfItems.add(this.item)
    }

    constructor(userId: String, items: List<Item>): this() {
        this.userId = userId
        accountInfo = UserAccountActivities().getAccountInfo(userId)!!
        this.items = items as MutableList<Item>
        this.finalizedListOfItems = this.items
    }
//
//    fun setFinalizedItems(item: Item? = null, items: MutableList<Item> = mutableListOf()) {
//        if(item != null) {
//            finalizedListOfItems.add(item)
//        } else {
//            if(items.isNotEmpty()) {
//                finalizedListOfItems = items
//            }
//        }
//    }

    fun openBuyNowPage() {
        orderedDate = "06-11-2022"
        deliveryDate = "16-11-2022"
        while(true) {
            displayItemDetails(finalizedListOfItems, orderedDate, deliveryDate)
            val buyNowPageDashboard = BuyNowPageDashboard.values()
            showDashboard("Buy now page", buyNowPageDashboard)
            when(getUserChoice(buyNowPageDashboard)) {
                BuyNowPageDashboard.SELECT_A_PRODUCT -> {

                }
                BuyNowPageDashboard.PROCEED_TO_BUY -> {
                    proceedToBuy()
                    break
                }
                BuyNowPageDashboard.GO_BACK -> break
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
                                        val ordersPage = OrdersPage()
                                        ordersPage.addOrdersToOrdersHistory(
                                            accountInfo.ordersHistoryId,
                                            finalizedListOfItems,
                                            orderedDate,
                                            deliveryDate,
                                            shippingAddress
                                        )
                                        if(::item.isInitialized) {
                                            if (cartActivities.removeFromCart(
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
                                            if (cartActivities.clearCartItems(accountInfo.cartId, items, true)) {
                                                println("Orders placed! Items removed from cart")
                                            }
                                        }
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
            println("Class BuyNowPage: showDashBoard(): Exception: ${exception.message}")
        }
    }

    private fun displayItemDetails(items: MutableList<Item>, orderedDate: String, deliveryDate: String) {
        items.forEachIndexed { index, item ->
            println("${index + 1}. $item\n" +
                    "Ordered Date : $orderedDate\n" +
                    "Delivery Date: $deliveryDate")
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
                println("Class BuyNowPage: getUserChoice(): Exception: $exception")
            }
        }
    }

}
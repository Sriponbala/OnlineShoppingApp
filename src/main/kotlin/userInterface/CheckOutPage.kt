package userInterface

import backend.CheckOutActivities
import data.*
import enums.*
import interfaces.DashboardServices
import utils.Helper
import java.time.LocalDate

class CheckOutPage(private val checkOutActivities: CheckOutActivities): DashboardServices {

    private lateinit var addressPage: AddressPage
    private lateinit var paymentPage: PaymentPage
    private var finalizedListOfItems = mutableListOf<LineItem>()
    private lateinit var finalizedItems: MutableList<Pair<ProductSku, StockStatus>>
    private var shippingAddress: Address? = null
    private var quantity = 1
    private lateinit var orderedDate: LocalDate
    private lateinit var payment: Payment
    private lateinit var accountInfo: AccountInfo
    private lateinit var item: LineItem
    private lateinit var items: MutableList<Triple<CartItem, ProductSku, StockStatus>>
    private var totalBill: Float = 0f
    private var isNavigatedFromCartPage: Boolean = false

    fun initializer(addressPage: AddressPage, paymentPage: PaymentPage, skuId: String, accountInfo: AccountInfo) {
        this.addressPage = addressPage
        this.paymentPage = paymentPage
        this.accountInfo = accountInfo
        this.orderedDate = Helper.generateOrderedDate()
        finalizedListOfItems = checkOutActivities.createItemToBuy(skuId, orderedDate, 1, update = false)
    }

    fun initializer(addressPage: AddressPage, paymentPage: PaymentPage, items: MutableList<Triple<CartItem, ProductSku, StockStatus>>, accountInfo: AccountInfo) {
        this.addressPage = addressPage
        this.paymentPage = paymentPage
        this.accountInfo = accountInfo
        this.items = items
        this.orderedDate = Helper.generateOrderedDate()
        for(product in items) {
            val selectedItems = checkOutActivities.createItemToBuy(product.first.skuId, orderedDate, product.first.quantity, update = false)
            for(item in selectedItems) {
                finalizedListOfItems.add(item)
            }
        }
        this.isNavigatedFromCartPage = true
    }

    fun openCheckOutPage() {
        while(true) {
            this.finalizedItems = checkOutActivities.getProductDetails(finalizedListOfItems)
            displayItemDetails(finalizedItems)
            val checkOutPageDashboard = ProductsManagement.values()
            super.showDashboard("CHECK OUT PAGE", checkOutPageDashboard)
            when(super.getUserChoice(checkOutPageDashboard)) {
                ProductsManagement.SELECT_A_PRODUCT -> {
                    if(finalizedItems.isNotEmpty()) {
                        val lineItem = selectAnItem()
                        doActivitiesOnSelectedItem(lineItem)
                    } else {
                        println("No items selected to buy!")
                    }
                }
                ProductsManagement.PROCEED_TO_BUY -> {
                    if(finalizedItems.isNotEmpty()) {
                        proceedToBuy()
                    } else {
                        println("No items selected to buy!")
                    }
                }
                ProductsManagement.GO_BACK -> {
                    finalizedListOfItems.clear()
                    checkOutActivities.clearLineItems()
                    finalizedItems.clear()
                    break
                }
            }
        }
    }

    private fun proceedToBuy() {
        try {
            label@while(true) {
                println("SELECT AN ADDRESS: ")
                addressPage.setSelectAddress(true)
                shippingAddress = addressPage.selectAddressForDelivery()
                if(shippingAddress != null) {
                    if(Helper.confirm()) {
                        val paymentPage = PaymentPage()
                        while(true) {
                            println("SELECT MODE OF PAYMENT: ")
                            payment = paymentPage.getPaymentMethod()
                            println("DO YOU WANT TO PLACE ORDER?")
                            if(Helper.confirm()) {
                                totalBill = checkOutActivities.getTotalBill(finalizedItems)
                                checkOutActivities.addAllLineItemsToDb()
                                checkOutActivities.updateStatusOfProducts()
                                checkOutActivities.createOrder(accountInfo.userId, orderedDate, shippingAddress!!, payment)
                                checkOutActivities.createOrderAndLineItemMapping(finalizedListOfItems)
                                paymentPage.pay(totalBill)
                                if(!isNavigatedFromCartPage) {
                                    item = finalizedListOfItems[0]
                                    if (checkOutActivities.removeFromCart(accountInfo.cartId, item, quantity)) {
                                        println("Order placed! Item removed from cart")
                                    } else {
                                        println("Order placed!")
                                    }
                                } else {
                                    checkOutActivities.clearCartItems(accountInfo.cartId, finalizedItems)
                                }
                                this.finalizedListOfItems.clear()
                                this.finalizedItems.clear()
                                checkOutActivities.clearLineItems()
                                addressPage.deselectShippingAddress()
                                break@label
                            } else {
                                break
                            }
                        }
                    } else {
                        addressPage.deselectShippingAddress()
                        break
                    }
                } else {
                    break
                }
            }
        } catch(exception: Exception) {
            println("Failed!")
        }
    }

    private fun displayItemDetails(
        finalizedItems: MutableList<Pair<ProductSku, StockStatus>>
    ) {
        finalizedItems.forEachIndexed { index, item ->
            println("""${index + 1}. Item Name        : ${item.first.productName}
                |   Item price       : ${item.first.price}
                |   Quantity         : ${checkOutActivities.getLineItemQuantity(item.first.skuId)}
                |   Status           : ${item.second.status}
            """.trimMargin())
        }
    }

    private fun selectAnItem(): LineItem {
        var option: Int
        var selectedItem: LineItem
        while(true){
            println("SELECT AN ITEM: ")
            try{
                val userInput = readLine()!!
                option = userInput.toInt()
                if(Helper.checkValidRecord(option, finalizedListOfItems.size)) {
                    selectedItem = finalizedListOfItems[option - 1]
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
                        val availableQuantity = checkOutActivities.getAvailableQuantityOfProduct(skuId)
                        if(availableQuantity >= input) {
                            quantity = input
                            break
                        } else {
                            println("Only $availableQuantity items available!")
                        }
                    } else {
                        if(input < 1) {
                            println("You should select atleast 1 item!")
                        } else {
                            println("You can select a maximum of 4 items!")
                        }
                    }
                } catch(exception: Exception) {
                    println("Enter valid option!")
                }
            } else break
        }
        return quantity
    }

    private fun doActivitiesOnSelectedItem(lineItem: LineItem) {
        val productQuantityManagement = ProductQuantityManagement.values()
        while(true) {
            super.showDashboard("ACTIVITIES ON SELECTED PRODUCT", productQuantityManagement)
            when(super.getUserChoice(productQuantityManagement)) {

                ProductQuantityManagement.CHANGE_QUANTITY -> {
                    this.quantity = getQuantity(lineItem.skuId)
                    if(Helper.confirm()) {
                        checkOutActivities.updateQuantityOfLineItem(lineItem, quantity)
                        finalizedListOfItems = checkOutActivities.getLineItems()
                    }
                }

                ProductQuantityManagement.REMOVE -> {
                    val iter = finalizedListOfItems.iterator()
                    for(it in iter) {
                        if (lineItem.skuId == it.skuId) {
                            iter.remove()
                        }
                    }
                    val i = finalizedItems.iterator()
                    for(finalizedItem in i) {
                        if(lineItem.skuId == finalizedItem.first.skuId) {
                            i.remove()
                        }
                    }
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
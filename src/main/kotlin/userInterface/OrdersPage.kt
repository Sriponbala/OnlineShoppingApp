package userInterface

import backend.OrdersHistoryManagement
import data.Item

class OrdersPage {


    private val ordersHistoryManagement = OrdersHistoryManagement()
    fun displayOrdersHistory(ordersHistoryId: String) {
        val ordersHistory = ordersHistoryManagement.getOrdersHistory(ordersHistoryId)
        println("-----------------Orders History---------------------")
        if(ordersHistory.isEmpty()){
            println("         Empty orders history         ")
        } else {
            ordersHistory.forEachIndexed { index, order ->
                println("${index + 1}. ${order.item.productName} - ${order.item.productPrice} - ${order.item.totalPrice} - ${order.item.quantity} - ${order.orderedDate} - ${order.deliveryDate}")
            }
        }
    }

    fun addOrdersToOrdersHistory(
        ordersHistoryId: String,
        finalizedListOfItems: MutableList<Item>,
        orderedDate: String,
        deliveryDate: String,
        shippingAddress: String
    ) {
        if(ordersHistoryManagement.addOrderToOrdersHistory(ordersHistoryId, finalizedListOfItems, orderedDate, deliveryDate, shippingAddress)) {
            println("Orders added to orders  history!")
        } else {
            println("Orders not added to orders history!")
        }
    }

}

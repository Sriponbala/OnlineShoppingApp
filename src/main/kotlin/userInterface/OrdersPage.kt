package userInterface

import backend.OrdersHistoryManagement

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

}

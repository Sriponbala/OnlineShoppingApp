package userInterface

import backend.OrdersHistoryActivities

class OrdersPage(private val ordersHistoryActivities: OrdersHistoryActivities) {

    private lateinit var ordersHistoryId: String

    fun initializer(ordersHistoryId: String) {
        this.ordersHistoryId = ordersHistoryId
    }

    fun displayOrdersHistory() {
        val ordersHistory = ordersHistoryActivities.getOrdersHistory(ordersHistoryId)
        println("-----------------ORDERS HISTORY---------------------")
        if(ordersHistory.isEmpty()){
            println("         Empty orders history         ")
        } else {
            ordersHistory.forEachIndexed { index, order ->
                println("${index + 1}. ${order.item.productName} - ${order.item.productPrice} - ${order.item.totalPrice} - ${order.item.quantity} - ${order.orderedDate} - ${order.deliveryDate}")
            }
        }
    }

}

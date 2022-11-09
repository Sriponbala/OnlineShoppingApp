package userInterface

import backend.OrdersHistoryActivities

class OrdersPage(private val ordersHistoryId: String) {


    private val ordersHistoryActivities = OrdersHistoryActivities()
    fun displayOrdersHistory() {
        val ordersHistory = ordersHistoryActivities.getOrdersHistory(ordersHistoryId)
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

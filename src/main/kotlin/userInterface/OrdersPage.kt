package userInterface

import backend.OrdersHistoryManagement

class OrdersPage(private val userId: String) {

    fun displayOrdersHistory() {
        val ordersHistory = OrdersHistoryManagement(userId).getOrdersHistory()
        println("-----------------Orders History---------------------")
        if(ordersHistory.isEmpty()){
            println("         Empty orders history         ")
        } else {
            for((orderId, order) in ordersHistory) {
                println("$orderId: ${order.product.productName} - ${order.product.price} - ${order.orderedDate} - ${order.deliveryDate}")
            }
        }
    }
}
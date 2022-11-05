package userInterface

import backend.OrdersHistoryManagement

class OrdersPage {


    private val ordersHistoryManagement = OrdersHistoryManagement()
    fun displayOrdersHistory(userId: String) {
        val ordersHistory = ordersHistoryManagement.getOrdersHistory(userId)
        println("-----------------Orders History---------------------")
        if(ordersHistory?.isEmpty() == true){
            println("         Empty orders history         ")
        } else {
            ordersHistory?.forEachIndexed { index, order ->
                println("${index + 1}. ${order.product.productName} - ${order.product.price} - ${order.orderedDate} - ${order.deliveryDate}")
            }
        } ?: println(" Empty orders history")
    }

}

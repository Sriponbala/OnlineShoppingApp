package userInterface

import backend.OrdersHistoryManagement

class OrdersPage(private val userId: String) {

    private val ordersHistoryManagement = OrdersHistoryManagement(this.userId)
    fun displayOrdersHistory() {
        val ordersHistory = ordersHistoryManagement.getOrdersHistory()
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

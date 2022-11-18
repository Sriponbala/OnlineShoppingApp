package userInterface

import backend.OrdersHistoryActivities
import java.time.format.DateTimeFormatter

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
                println("""${index + 1}. PRODUCT NAME : ${order.item.productName}
                    |   PRODUCT PRICE    : ${order.item.productPrice}
                    |   QUANTITY         : ${order.item.quantity}
                    |   TOTAL PRICE      : ${order.item.totalPrice}
                    |   ORDERED DATE     : ${order.orderedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))}
                    |   DELIVERY DATE    : ${order.deliveryDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))}
                    |   SHIPPING ADDRESS : ${order.shippingAddress}
                """.trimMargin())
            }
        }
    }

}

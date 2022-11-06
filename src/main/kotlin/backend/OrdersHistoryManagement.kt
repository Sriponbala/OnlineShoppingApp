package backend

import data.Order
import utils.OrdersData

class OrdersHistoryManagement {

    private val ordersData = OrdersData()

    fun addOrderToOrdersHistory(userId: String, order: Order) {
        ordersData.addToOrdersHistory(userId, order)
    }

    fun getOrdersHistory(ordersHistoryId: String): ArrayList<Order>? {
        return ordersData.retrieveOrdersHistory(ordersHistoryId)
    }

    fun getOrdersHistoryId(userId: String): String {
        val id: String = if(ordersData.retrieveOrdersHistoryId(userId) == null) {
            ""
        } else {
            ordersData.retrieveOrdersHistoryId(userId)!!
        }
        return id
    }

    fun createAndGetOrdersHistoryId(userId: String): String {
        return ordersData.createAndGetOrdersHistoryId(userId)
    }
}


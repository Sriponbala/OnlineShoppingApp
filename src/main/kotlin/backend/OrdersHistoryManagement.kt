package backend

import data.Order
import data.OrdersHistoryRecord
import utils.OrdersData

class OrdersHistoryManagement {

    private val ordersData = OrdersData()

    fun addOrderToOrdersHistory(userId: String, order: Order) {
        ordersData.addToOrdersHistory(userId, order)
    }

    fun getOrdersHistory(userId: String): ArrayList<Order>? {
        return ordersData.retrieveOrdersHistory(userId)
    }
}


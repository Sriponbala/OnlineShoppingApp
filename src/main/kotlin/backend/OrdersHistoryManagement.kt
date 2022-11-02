package backend

import data.Order
import data.OrdersHistoryRecord
import utils.OrdersData

class OrdersHistoryManagement(private val userId: String) {

    private val ordersData = OrdersData()

    fun createOrdersHistoryRecord() {
        ordersData.createOrdersHistoryRecord(OrdersHistoryRecord(userId, mutableMapOf()))
    }

    fun addOrderToOrdersHistory(order: Order) {
        ordersData.addToOrdersHistory(userId, order)
    }

    fun getOrdersHistory(): MutableMap<String, Order> {
        return ordersData.retrieveOrdersHistory(userId)
    }
}

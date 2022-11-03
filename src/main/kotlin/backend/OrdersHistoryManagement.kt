package backend

import data.Order
import data.OrdersHistoryRecord
import utils.OrdersData

class OrdersHistoryManagement(private val userId: String) {

    private val ordersData = OrdersData(userId = this.userId)

    fun addOrderToOrdersHistory(order: Order) {
        ordersData.addToOrdersHistory(userId, order)
    }

    fun getOrdersHistory(): ArrayList<Order>? {
        return ordersData.retrieveOrdersHistory(userId)
    }
}


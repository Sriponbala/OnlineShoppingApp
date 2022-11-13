package interfaces

import data.Order

interface OrdersDao {

    fun retrieveOrdersHistory(ordersHistoryId: String): ArrayList<Order>

    fun addToOrdersHistory(ordersHistoryId: String, orders: ArrayList<Order>)

    fun createAndGetOrdersHistoryId(): String

}
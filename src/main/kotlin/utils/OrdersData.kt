package utils

import data.Order
import database.OrdersTable
import interfaces.OrdersDao

class OrdersData: OrdersDao {

    private lateinit var ordersHistoryId: String

    override fun retrieveOrdersHistory(ordersHistoryId: String): ArrayList<Order> {
        return OrdersTable.usersOrdersHistory[ordersHistoryId]!!
    }

    override fun addToOrdersHistory(ordersHistoryId: String, orders: ArrayList<Order>) {
        for(order in orders) {
            retrieveOrdersHistory(ordersHistoryId).add(order)
        }
    }

    private fun createOrdersHistory() {
        ordersHistoryId = OrdersTable.generateOrdersHistoryId()
        OrdersTable.usersOrdersHistory[ordersHistoryId] = arrayListOf() // ArrayList<Order>
    }

    override fun createAndGetOrdersHistoryId(): String {
        createOrdersHistory()
        return ordersHistoryId
    }

}
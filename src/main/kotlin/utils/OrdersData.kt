package utils

import data.Order
import database.OrdersTable

class OrdersData {

//    private late init var ordersDatabase: OrdersDatabase
//
//    private fun getConnection() {
//        ordersDatabase = OrdersDatabase.getInstance(dbUserName, dbPassword)!!
//    }
//
//    init {
//        getConnection()
//        createOrdersHistoryRecord(OrdersHistoryRecord(this.userId))
//    }

    private lateinit var ordersHistoryId: String

    fun retrieveOrdersHistory(ordersHistoryId: String): ArrayList<Order> {
        return OrdersTable.usersOrdersHistory[ordersHistoryId]!!
    }

    fun addToOrdersHistory(ordersHistoryId: String, orders: ArrayList<Order>) {
        for(order in orders) {
            retrieveOrdersHistory(ordersHistoryId).add(order)
        }
    }

    private fun createOrdersHistory() {
        ordersHistoryId = OrdersTable.generateOrdersHistoryId()
        OrdersTable.usersOrdersHistory[ordersHistoryId] = arrayListOf() // ArrayList<Order>
    }

    fun createAndGetOrdersHistoryId(): String {
        createOrdersHistory()
        return ordersHistoryId
    }

}
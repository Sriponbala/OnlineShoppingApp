package utils

import data.Order
import database.OrdersDatabase
import data.OrdersHistoryRecord

class OrdersData(private val dbUserName: String = "root",
                 private val dbPassword: String = "tiger",
                 private val userId: String) {

    private lateinit var ordersDatabase: OrdersDatabase

    private fun getConnection() {
        ordersDatabase = OrdersDatabase.getInstance(dbUserName, dbPassword)!!
    }

    init {
        getConnection()
        createOrdersHistoryRecord(OrdersHistoryRecord(this.userId))
    }

    fun retrieveOrdersHistory(userId: String): ArrayList<Order>? {
        return ordersDatabase.getOrdersHistoryOfUser(userId)
    }

    fun addToOrdersHistory(userId: String, order: Order) {
        ordersDatabase.addOrderToOrdersHistoryOfUser(userId, order)
    }

    private fun createOrdersHistoryRecord(ordersHistoryRecord: OrdersHistoryRecord) {
        ordersDatabase.addOrdersHistoryRecordOfUser(ordersHistoryRecord)
    }
}
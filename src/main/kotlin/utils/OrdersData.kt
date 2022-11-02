package utils

import data.Order
import database.OrdersDatabase
import data.OrdersHistoryRecord

class OrdersData(private val dbUserName: String = "root",
                 private val dbPassword: String = "tiger") {

    private lateinit var ordersDatabase: OrdersDatabase

    private fun getConnection() {
        ordersDatabase = OrdersDatabase.getInstance(dbUserName, dbPassword)!!
    }

    init {
        getConnection()
    }

    fun retrieveOrdersHistory(userId: String): MutableMap<String, Order> {
        return ordersDatabase.getOrdersHistoryOfUser(userId)
    }

    fun addToOrdersHistory(userId: String, order: Order) {
        ordersDatabase.addOrderToOrdersHistoryOfUser(userId, order)
    }

    fun createOrdersHistoryRecord(ordersHistoryRecord: OrdersHistoryRecord) {
        ordersDatabase.addOrdersHistoryRecordOfUser(ordersHistoryRecord)
    }
}
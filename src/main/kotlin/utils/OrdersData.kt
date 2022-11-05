package utils

import data.Order
import database.OrdersDatabase
import data.OrdersHistoryRecord

class OrdersData(private val dbUserName: String = "root",
                 private val dbPassword: String = "tiger") {

//    private lateinit var ordersDatabase: OrdersDatabase
//
//    private fun getConnection() {
//        ordersDatabase = OrdersDatabase.getInstance(dbUserName, dbPassword)!!
//    }
//
//    init {
//        getConnection()
//        createOrdersHistoryRecord(OrdersHistoryRecord(this.userId))
//    }

    fun retrieveOrdersHistory(userId: String): ArrayList<Order>? {
        val ordersHistory: ArrayList<Order>? = if(OrdersDatabase.usersOrdersHistory.containsKey(userId)) {
            OrdersDatabase.usersOrdersHistory[userId]?.ordersHistory
        } else {
            null
        }
        return ordersHistory
    }

    fun addToOrdersHistory(userId: String, order: Order) {
        if(OrdersDatabase.usersOrdersHistory.containsKey(userId)) {
            OrdersDatabase.usersOrdersHistory[userId]?.ordersHistory?.add(order)
        }
    }

    private fun createOrdersHistoryRecord(ordersHistoryRecord: OrdersHistoryRecord) {
        OrdersDatabase.usersOrdersHistory[ordersHistoryRecord.userId] = ordersHistoryRecord
    }
}
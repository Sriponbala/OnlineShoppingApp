package utils

import data.Order
import database.OrdersDatabase
import database.UsersDatabase

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

    fun retrieveOrdersHistory(ordersHistoryId: String): ArrayList<Order>? {
        val ordersHistory: ArrayList<Order>? = if(OrdersDatabase.usersOrdersHistory.containsKey(ordersHistoryId)) {
            OrdersDatabase.usersOrdersHistory[ordersHistoryId]
        } else {
            null
        }
        return ordersHistory
    }

    fun addToOrdersHistory(ordersHistoryId: String, order: Order) {
        if(OrdersDatabase.usersOrdersHistory.containsKey(ordersHistoryId)) {
            OrdersDatabase.usersOrdersHistory[ordersHistoryId]?.add(order)
        }
    }

    private fun createOrdersHistory() {
        ordersHistoryId = OrdersDatabase.generateOrdersHistoryId()
        OrdersDatabase.usersOrdersHistory[ordersHistoryId] = arrayListOf() // ArrayList<Order>
    }


    fun retrieveOrdersHistoryId(userId: String): String? {
        var id: String? = null
        if(UsersDatabase.users.containsKey(userId)) {
            if(UsersDatabase.usersAccountInfo.containsKey(userId)) {
                id = UsersDatabase.usersAccountInfo[userId]?.ordersHistoryId
            }
        }
        return id
    }

    fun createAndGetOrdersHistoryId(userId: String): String {
        var id = ""
        if(UsersDatabase.users.containsKey(userId)) {
            createOrdersHistory()
            id = ordersHistoryId
        }
        return id
    }


}
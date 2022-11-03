package database

import data.Order
import data.OrdersHistoryRecord
import utils.Helper

class OrdersDatabase private constructor() {

    private val usersOrdersHistory: MutableMap<String, OrdersHistoryRecord> = mutableMapOf() // userId, ordersHistoryRecord
    companion object {
        private val INSTANCE by lazy { OrdersDatabase() }
        private const val USERNAME = "root"
        private const val PASSWORD = "tiger"
        fun getInstance(userName: String, password: String): OrdersDatabase? {

            return if (userName == USERNAME && password == PASSWORD) {
                INSTANCE
            } else {
                null
            }
        }
    }

    fun addOrdersHistoryRecordOfUser(ordersHistoryRecord: OrdersHistoryRecord) {

        usersOrdersHistory[ordersHistoryRecord.userId] = ordersHistoryRecord
    }

    fun getOrdersHistoryOfUser(userId: String): ArrayList<Order>? {

        val ordersHistory: ArrayList<Order>? = if(usersOrdersHistory.containsKey(userId)) {
            usersOrdersHistory[userId]?.ordersHistory
        } else {
            null
        }
        return ordersHistory
    }

    fun addOrderToOrdersHistoryOfUser(userId: String, order: Order) {

        if(usersOrdersHistory.containsKey(userId)) {
            usersOrdersHistory[userId]?.ordersHistory?.add(order)
        }
    }
}
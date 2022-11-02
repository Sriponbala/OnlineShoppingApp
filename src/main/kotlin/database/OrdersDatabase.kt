package database

import data.Order
import data.OrdersHistoryRecord
import utils.Helper

class OrdersDatabase private constructor() {

    private val usersOrdersHistory: MutableMap<String, OrdersHistoryRecord> = mutableMapOf() // id, ordersHistoryRecord
    companion object {
        private val INSTANCE by lazy { OrdersDatabase() }
        fun getInstance(userName: String, password: String): OrdersDatabase? {

            return if (userName == "root" && password == "tiger") {
                INSTANCE
            } else {
                null
            }
        }
    }

    fun addOrdersHistoryRecordOfUser(ordersHistoryRecord: OrdersHistoryRecord) {

        usersOrdersHistory[Helper.generateRecordId()] = ordersHistoryRecord
    }

    fun getOrdersHistoryOfUser(userId: String): MutableMap<String, Order> {
        var ordersHistory = mutableMapOf<String, Order>()
        for((recordId, ordersHistoryRecord) in usersOrdersHistory) {
            if(ordersHistoryRecord.userId == userId) {
                ordersHistory = usersOrdersHistory[recordId]?.ordersHistory!!
                break
            }
        }
        return ordersHistory
    }

    fun addOrderToOrdersHistoryOfUser(userId: String, order: Order) {

        for((recordId, ordersHistoryRecord) in usersOrdersHistory) {
            if(ordersHistoryRecord.userId == userId) {
                usersOrdersHistory[recordId]?.ordersHistory?.set(Helper.generateOrderId(), order)
                break
            }
        }
    }
}
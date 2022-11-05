package database

import data.OrdersHistoryRecord


object OrdersDatabase {

    val usersOrdersHistory: MutableMap<String, OrdersHistoryRecord> = mutableMapOf() // userId, ordersHistoryRecord

}


//class OrdersDatabase private constructor() {

//    companion object {
//        private val INSTANCE by lazy { OrdersDatabase() }
//        private const val USERNAME = "root"
//        private const val PASSWORD = "tiger"
//        fun getInstance(userName: String, password: String): OrdersDatabase? {
//
//            return if (userName == USERNAME && password == PASSWORD) {
//                INSTANCE
//            } else {
//                null
//            }
//        }
//    }






//fun addOrdersHistoryRecordOfUser(ordersHistoryRecord: OrdersHistoryRecord) {
//
//    OrdersDatabase.usersOrdersHistory[ordersHistoryRecord.userId] = ordersHistoryRecord
//}
//
//fun getOrdersHistoryOfUser(userId: String): ArrayList<Order>? {
//
//    val ordersHistory: ArrayList<Order>? = if(OrdersDatabase.usersOrdersHistory.containsKey(userId)) {
//        OrdersDatabase.usersOrdersHistory[userId]?.ordersHistory
//    } else {
//        null
//    }
//    return ordersHistory
//}
//
//fun addOrderToOrdersHistoryOfUser(userId: String, order: Order) {
//
//    if(OrdersDatabase.usersOrdersHistory.containsKey(userId)) {
//        OrdersDatabase.usersOrdersHistory[userId]?.ordersHistory?.add(order)
//    }
//}
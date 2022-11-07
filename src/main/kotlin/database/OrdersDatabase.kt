package database

import data.Order


object OrdersDatabase {

    val usersOrdersHistory: MutableMap<String, ArrayList<Order>> = mutableMapOf() // ordersHistoryId, ArrayList<Order>

    private var ordersHistoryId = 1

    fun generateOrdersHistoryId(): String {
        return "OH${ordersHistoryId++}"
    }

    private var orderId = 1

    fun generateOrderId(): String {
        return "Order${orderId++}"
    }

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
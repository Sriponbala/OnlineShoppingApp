package database

import data.Order


object OrdersTable {

    val usersOrdersHistory: MutableMap<String, ArrayList<Order>> = mutableMapOf() // ordersHistoryId, ArrayList<Order>

    private var ordersHistoryId = 1

    fun generateOrdersHistoryId(): String {
        return "OH${ordersHistoryId++}"
    }

}






















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
package data

data class OrdersHistoryRecord(var userId: String, val ordersHistory: MutableMap<String, Order> = mutableMapOf())

package data

data class OrdersHistoryRecord(var userId: String, val ordersHistory: ArrayList<Order> = arrayListOf())

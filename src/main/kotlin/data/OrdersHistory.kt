package data

data class OrdersHistory(var userId: String, val ordersHistory: MutableList<Order> = mutableListOf())

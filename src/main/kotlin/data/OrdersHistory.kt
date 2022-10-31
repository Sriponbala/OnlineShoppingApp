package data

data class OrdersHistory(var userMobile: String, val ordersHistory: MutableList<Order> = mutableListOf())

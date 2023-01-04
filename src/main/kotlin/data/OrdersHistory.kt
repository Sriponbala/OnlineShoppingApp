package data

import utils.Helper

data class OrdersHistory(val userId: String) {

    val ordersHistoryId: String = Helper.generateOrdersHistoryId()
}

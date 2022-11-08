package backend

import data.Item
import data.Order
import utils.OrdersData
import utils.Utility

class OrdersHistoryManagement {

    private val ordersData = OrdersData()
    private val utility = Utility()

    fun addOrderToOrdersHistory(ordersHistoryId: String, orders: ArrayList<Order>): Boolean {
        return if(utility.checkIfOrdersHistoryExists(ordersHistoryId)) {
            ordersData.addToOrdersHistory(ordersHistoryId, orders)
            true
        } else false
    }

    fun getOrdersHistory(ordersHistoryId: String): ArrayList<Order> {
        return if(utility.checkIfOrdersHistoryExists(ordersHistoryId)) {
            ordersData.retrieveOrdersHistory(ordersHistoryId)
        } else arrayListOf()
    }

    fun getOrdersHistoryId(userId: String): String {
        val id: String = if(ordersData.retrieveOrdersHistoryId(userId) == null) {
            ""
        } else {
            ordersData.retrieveOrdersHistoryId(userId)!!
        }
        return id
    }

    fun createAndGetOrdersHistoryId(userId: String): String {
        var ordersHistoryId = ""
        if(utility.checkIfUserExists(userId)) {
            ordersHistoryId = ordersData.createAndGetOrdersHistoryId()
        }
        return ordersHistoryId
    }
}


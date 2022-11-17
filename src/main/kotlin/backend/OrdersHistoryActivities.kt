package backend

import data.Order
import interfaces.OrdersDao
import interfaces.UtilityDao
import utils.OrdersData

class OrdersHistoryActivities(private val utility: UtilityDao) {

    private val ordersDao: OrdersDao = OrdersData()

    fun addOrderToOrdersHistory(ordersHistoryId: String, orders: ArrayList<Order>): Boolean {

        return if(utility.checkIfOrdersHistoryExists(ordersHistoryId)) {
            ordersDao.addToOrdersHistory(ordersHistoryId, orders)
            true
        } else false
    }

    fun getOrdersHistory(ordersHistoryId: String): ArrayList<Order> {

        return if(utility.checkIfOrdersHistoryExists(ordersHistoryId)) {
            ordersDao.retrieveOrdersHistory(ordersHistoryId)
        } else arrayListOf()
    }

    fun createAndGetOrdersHistoryId(userId: String): String {

        var ordersHistoryId = ""
        if(utility.checkIfUserExists(userId)) {
            ordersHistoryId = ordersDao.createAndGetOrdersHistoryId()
        }
        return ordersHistoryId
    }
}


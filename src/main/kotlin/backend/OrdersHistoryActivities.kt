package backend

import data.Order
import interfaces.OrdersDao
import interfaces.UtilityDao

class OrdersHistoryActivities(private val utility: UtilityDao, private val ordersDao: OrdersDao) {

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

    fun createAndGetOrdersHistoryId(): String {
        return ordersDao.createAndGetOrdersHistoryId()
    }
}


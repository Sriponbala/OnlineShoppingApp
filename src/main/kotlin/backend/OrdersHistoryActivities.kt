package backend

import data.*
import enums.Payment
import interfaces.OrdersDao
import java.time.LocalDate

class OrdersHistoryActivities(private val productActivities: ProductActivities, private val ordersDao: OrdersDao) {

    private lateinit var orderId: String

    fun getOrdersHistory(userId: String): MutableList<Triple<Order, ProductSku, Int>> {
        val ordersHistory: MutableList<Triple<Order, ProductSku, Int>> = mutableListOf()
        val tempOrdersList: MutableList<Pair<Order, ProductSku>> = mutableListOf()
        val tempQuantityMap: MutableMap<String, Int> = mutableMapOf()
        val ordersHistoryDetails = getOrdersHistoryDetails(userId)
        for(orderDetails in ordersHistoryDetails) {
            if(tempOrdersList.isEmpty()) {
                val order = ordersDao.getOrder(orderDetails.orderId)
                val lineItem = ordersDao.getLineItem(orderDetails.lineItemId)
                val productSku = productActivities.getProductDetails(lineItem.skuId).first// Pair<ProductSku, Status>.first = ProductSku
                val tempOrder = Pair(order, productSku)
                tempOrdersList.add(tempOrder)
                tempQuantityMap[productSku.skuId] = 1
            } else {
                val order = ordersDao.getOrder(orderDetails.orderId)
                val lineItem = ordersDao.getLineItem(orderDetails.lineItemId)
                var flag = false
                for(tempOrder in tempOrdersList) {
                    if(lineItem.skuId == tempOrder.second.skuId) {
                        tempQuantityMap[lineItem.skuId] = tempQuantityMap[lineItem.skuId]!! + 1
                        flag = true
                        break
                    }
                }
                if(!flag) {
                    val productSku = productActivities.getProductDetails(lineItem.skuId).first// Pair<ProductSku, Status>.first = ProductSku
                    val tempOrder = Pair(order, productSku)
                    tempOrdersList.add(tempOrder)
                    tempQuantityMap[productSku.skuId] = 1
                }
            }
        }
        for(tempOrder in tempOrdersList) {
            ordersHistory.add(Triple(tempOrder.first, tempOrder.second, tempQuantityMap[tempOrder.second.skuId]!!))
        }
        return ordersHistory
    }

    private fun getOrdersHistoryDetails(userId: String): MutableList<OrderIdLineItemMapping> {
        return ordersDao.retrieveOrdersHistory(userId)
    }

    fun addLineItemsToDb(lineItems: MutableList<LineItem>) {
        ordersDao.addLineItemsToDb(lineItems)
    }

    fun createOrder(userId: String, orderedDate: LocalDate, shippingAddress: Address, payment: Payment) {
        val order = Order(userId, orderedDate, shippingAddress, payment)
        this.orderId = order.orderId
        ordersDao.createOrder(order)
    }

    fun createOrderAndLineItemMapping(lineItems: MutableList<LineItem>) {
        ordersDao.createOrderAndLineItemMapping(orderId, lineItems)
    }

}


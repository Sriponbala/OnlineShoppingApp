package utils

import data.*
import database.*
import interfaces.OrdersDao

class OrdersData(private val userName: String = "root",
                 private val password: String = "tiger"): OrdersDao {

    private val database: Database = Database.getConnection(this.userName, this.password)!!


    override fun retrieveOrdersHistory(ordersHistoryId: String): MutableList<OrderIdLineItemMapping> {
        val ordersHistory: MutableList<OrderIdLineItemMapping> = mutableListOf()
        for(order in database.orders) {
            if(ordersHistoryId == order.ordersHistoryId) {
                for(orderLineItemMapping in database.orderLineItemMappings) {
                    if(order.orderId == orderLineItemMapping.orderId) {
                        ordersHistory.add(orderLineItemMapping)
                    }
                }
            }
        }
        return ordersHistory
    }

    override fun createAndGetOrdersHistoryId(userId: String): String {
        val ordersHistory = OrdersHistory(userId)
        val ordersHistoryId = ordersHistory.ordersHistoryId
        database.usersOrdersHistory.add(ordersHistory)
        return ordersHistoryId
    }

    override fun getLineItemQuantity(skuId: String): Int {
        var quantity = 0
        for(lineItem in database.lineItems) {
            if(skuId == lineItem.skuId) {
                quantity += 1
            }
        }
        return quantity
    }

    override fun addLineItemsToDb(lineItems: MutableList<LineItem>) {
        for(lineItem in lineItems) {
            database.lineItems.add(lineItem)
        }
    }

    override fun createOrder(order: Order) {
        database.orders.add(order)
    }

    override fun getLineItem(lineItemId: String): LineItem {
        var item: LineItem? = null
        for(lineItem in database.lineItems) {
            if(lineItemId == lineItem.lineItemId) {
                item = lineItem
                break
            }
        }
        return item!!
    }

    override fun getOrder(orderId: String): Order {
        var order: Order? = null
        for(localOrder in database.orders) {
            if(orderId == localOrder.orderId) {
                order = localOrder
                break
            }
        }
        return order!!
    }

    override fun createOrderAndLineItemMapping(orderId: String, lineItems: MutableList<LineItem>) {
        for(lineItem in lineItems) {
            database.orderLineItemMappings.add(OrderIdLineItemMapping(orderId, lineItem.lineItemId))
        }
    }

}
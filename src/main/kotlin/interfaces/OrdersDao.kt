package interfaces

import data.LineItem
import data.Order
import data.OrderIdLineItemMapping

interface OrdersDao {

    fun retrieveOrdersHistory(ordersHistoryId: String): MutableList<OrderIdLineItemMapping>

    fun createAndGetOrdersHistoryId(userId: String): String

    fun getLineItemQuantity(skuId: String): Int

    fun addLineItemsToDb(lineItems: MutableList<LineItem>)

    fun createOrder(order: Order)

    fun getLineItem(lineItemId: String): LineItem

    fun getOrder(orderId: String): Order

    fun createOrderAndLineItemMapping(orderId: String, lineItems: MutableList<LineItem>)

}
package backend

import data.Item
import data.Order

class CheckOutActivities(
    private val cartActivities: CartActivities,
    private val productActivities: ProductActivities,
    private val ordersHistoryActivities: OrdersHistoryActivities
) {

    private val orders: ArrayList<Order> = arrayListOf()

    fun addOrdersToOrdersHistory(
        ordersHistoryId: String,
        orders: ArrayList<Order>,
    ): Boolean {

        return ordersHistoryActivities.addOrderToOrdersHistory(ordersHistoryId, orders)
    }

    fun createOrders(finalizedListOfItems: MutableList<Item>, shippingAddress: String) {

        val orderedDate = "1-1-2022"
        val deliveryDate = "2-2-2022"
        for(item in finalizedListOfItems) {
            orders.add(Order(orderId = generateOrderId(), orderedDate = orderedDate, deliveryDate = deliveryDate, shippingAddress = shippingAddress, item = item))
        }
    }

    fun getOrders(): ArrayList<Order> {

        return orders
    }

    fun clearOrders() {

        this.orders.clear()
    }

    private var orderId = 1

    private fun generateOrderId(): String {

        return "Order${orderId++}"
    }

    fun removeFromCart(cartId: String, productId: String, remove: Boolean): Boolean {

        return cartActivities.removeFromCart(cartId, productId, remove)
    }

    fun clearCartItems(cartId: String, cartItems: ArrayList<Item>, remove: Boolean): Boolean {

        return cartActivities.clearCartItems(cartId, cartItems, remove)
    }

    fun createItemToBuy(productId: String, category: String): Item {

        val product = productActivities.getProductFromDb(productId, category)
        return Item(product.productId, product.productName, product.price, product.price, category, 1, product.status)
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {

        return productActivities.getAvailableQuantityOfProduct(productId, category)
    }

    fun changeQuantityAndUpdateTotalPriceOfItem(item: Item, quantity: Int) {

        item.quantity = quantity
        item.totalPrice = (quantity * item.productPrice)
    }

    fun getTotalBill(finalizedListOfItems: ArrayList<Item>): Float {

        var totalBill = 0f
        for(item in finalizedListOfItems) {
            totalBill += item.totalPrice
        }
        return totalBill
    }

    fun updateAvailableQuantityAndStatusOfProducts(finalizedListOfItems: ArrayList<Item>) {

        for(item in finalizedListOfItems) {
            productActivities.updateAvailableQuantityAndStatusOfProduct(item.productId, item.category, item.quantity)
        }
    }

    fun updateAvailableQuantityAndStatusOfCartItems() {

        cartActivities.updateAvailabilityStatusOfCartItems()
    }

}
package backend

import data.Item
import data.Order
import enums.ProductStatus
import utils.Utility

class CheckOutActivities() {

    private val ordersHistoryManagement = OrdersHistoryManagement()
    private val cartActivities = CartActivities()
    private val utility = Utility()
    private val orders: ArrayList<Order> = arrayListOf()
    private val shopActivities = ShopActivities()



    fun addOrdersToOrdersHistory(
        ordersHistoryId: String,
        orders: ArrayList<Order>,
    ): Boolean {
        return ordersHistoryManagement.addOrderToOrdersHistory(ordersHistoryId, orders)
    }

    fun createOrders(finalizedListOfItems: MutableList<Item>) {
        val orderedDate = "1-1-2022"
        val deliveryDate = "2-2-2022"
        for(item in finalizedListOfItems) {
            orders.add(Order(orderId = generateOrderId(), orderedDate = orderedDate, deliveryDate = deliveryDate, item = item))
        }
    }

    fun getOrders(): ArrayList<Order> {
        return orders
    }

    fun clearOrders() {
        this.orders.clear()
    }

    private var orderId = 1

    fun generateOrderId(): String {
        return "Order${orderId++}"
    }

    fun removeFromCart(cartId: String, productId: String, remove: Boolean): Boolean {
        return cartActivities.removeFromCart(cartId, productId, remove)
    }

    fun clearCartItems(cartId: String, cartItems: ArrayList<Item>, remove: Boolean): Boolean {
        return cartActivities.clearCartItems(cartId, cartItems, remove)
    }

    fun createItemToBuy(productId: String, category: String): Item {
        val product = shopActivities.getProductFromDb(productId, category)
        return Item(product.productId, product.productName, product.price, product.price, category, 1, product.status)
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {
        return shopActivities.getAvailableQuantityOfProduct(productId, category)
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
            shopActivities.updateAvailableQuantityAndStatusOfProduct(item.productId, item.category, item.quantity)
        }
    }

    fun updateAvailableQuantityAndStatusOfCartItems() {
        cartActivities.updateAvailabilityStatusOfCartItems()
    }

}
package backend

import data.*
import enums.Payment
import java.time.LocalDate

class CheckOutActivities(
    private val cartActivities: CartActivities,
    private val productActivities: ProductActivities,
    private val ordersHistoryActivities: OrdersHistoryActivities
) {

    private var lineItems: MutableList<LineItem> = mutableListOf()

    fun clearLineItems() {
        this.lineItems = mutableListOf()
    }

    fun removeFromCart(cartId: String, lineItem: LineItem, quantity: Int): Boolean {
        return cartActivities.removeFromCart(cartId, lineItem.skuId, quantity)
    }

    fun clearCartItems(cartId: String, cartItems: MutableList<Pair<ProductSku, Filters.StatusFilters>>) {
        for(cartItem in cartItems) {
            cartActivities.removeFromCart(cartId, cartItem.first.skuId, getLineItemQuantity(cartItem.first.skuId))
        }
    }

    fun createItemToBuy(skuId: String, orderedDate: LocalDate, quantity: Int, update: Boolean): MutableList<LineItem> {
        val productDetails: MutableList<ProductDetails> = if(update) {
            productActivities.getProducts(skuId, quantity, lineItems)
        } else {
            productActivities.getProducts(skuId, quantity)
        }
        val tempLineItems: MutableList<LineItem> = mutableListOf()
        for(product in productDetails) {
            val lineItem = LineItem(skuId, product.productId, orderedDate)
            tempLineItems.add(lineItem)
            lineItems.add(lineItem)
        }
        return tempLineItems
    }

    fun getProductDetails(items: MutableList<LineItem>): MutableList<Pair<ProductSku, Filters.StatusFilters>> {
        val finalizedSet: MutableSet<Pair<ProductSku, Filters.StatusFilters>> = mutableSetOf()
        val finalizedItems: MutableList<Pair<ProductSku, Filters.StatusFilters>> = mutableListOf()
        for(item in items) {
            finalizedSet.add(productActivities.getProductDetails(item.skuId))
        }
        for(setItem in finalizedSet) {
            finalizedItems.add(setItem)
        }
        return finalizedItems
    }

    fun getAvailableQuantityOfProduct(skuId: String): Int {
        return productActivities.getAvailableQuantityOfProduct(skuId)
    }

    fun getTotalBill(finalizedItems: MutableList<Pair<ProductSku, Filters.StatusFilters>>): Float {
        var totalBill = 0f
        for(item in finalizedItems) {
            totalBill += (item.first.price * getLineItemQuantity(item.first.skuId))
        }
        return totalBill
    }

    fun updateStatusOfProducts() {
        for(lineItem in lineItems) {
            productActivities.updateStatusOfProduct(lineItem)
        }
    }

    fun getLineItemQuantity(skuId: String): Int {
        var quantity = 0
        for(lineItem in lineItems) {
            if(skuId == lineItem.skuId) {
                quantity += 1
            }
        }
        return quantity
    }

    fun updateQuantityOfLineItem(lineItem: LineItem, quantity: Int) {
        val newQuantity: Int
        if(quantity > getLineItemQuantity(lineItem.skuId)) {
            newQuantity = quantity - getLineItemQuantity(lineItem.skuId)
            createItemToBuy(lineItem.skuId, lineItem.orderedDate, newQuantity, update = true)
        } else if (quantity < getLineItemQuantity(lineItem.skuId)) {
            newQuantity = getLineItemQuantity(lineItem.skuId) - quantity
            var count = 0
            val iter = lineItems.iterator()
            for(it in iter) {
                if(count < newQuantity) {
                    if(lineItem.skuId == it.skuId) {
                        iter.remove()
                        count++
                    }
                } else break
            }
        }
    }

    fun addAllLineItemsToDb() {
        ordersHistoryActivities.addLineItemsToDb(lineItems)
    }

    fun createOrder(ordersHistoryId: String, orderedDate: LocalDate, shippingAddress: Address, payment: Payment) {
        ordersHistoryActivities.createOrder(ordersHistoryId, orderedDate, shippingAddress, payment)
    }

    fun createOrderAndLineItemMapping(finalizedListOfItems: MutableList<LineItem>) {
        ordersHistoryActivities.createOrderAndLineItemMapping(finalizedListOfItems)
    }

    fun getLineItems() = lineItems

}
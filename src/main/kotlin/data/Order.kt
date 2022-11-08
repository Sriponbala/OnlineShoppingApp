package data

data class Order(val orderId: String, val orderedDate: String, val deliveryDate: String, var shippingAddress: String = "", val item: Item) {

    override fun toString(): String {
        return """$item
            |Shipping Address : $shippingAddress
            |Ordered Date     : $orderedDate
            |Delivery Date    : $deliveryDate
        """.trimMargin()
    }
}

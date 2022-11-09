package data

data class Order(val orderId: String, val orderedDate: String, val deliveryDate: String, var shippingAddress: String = "", val item: Item)


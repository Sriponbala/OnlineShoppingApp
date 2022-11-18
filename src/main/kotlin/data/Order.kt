package data

import java.time.LocalDate

data class Order(val orderId: String, val orderedDate: LocalDate, val deliveryDate: LocalDate, var shippingAddress: String = "", val item: Item)


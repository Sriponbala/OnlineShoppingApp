package data

import enums.Payment
import utils.Helper
import java.time.LocalDate

data class Order(val userId: String, val orderedDate: LocalDate, val shippingAddress: Address, val payment: Payment) {

    val orderId = Helper.generateOrderId()
}


package data

import utils.Helper

data class Cart(val userId: String) {
    val cartId = Helper.generateCartId()
    var subTotal: Float = 0f
}




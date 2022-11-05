package data

data class Cart(val cartId: String, val cartItems: MutableList<Item>, var subTotal: Float = 0f)


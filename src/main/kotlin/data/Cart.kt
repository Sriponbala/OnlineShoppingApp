package data

data class Cart(val cartItems: MutableList<Item> = mutableListOf(), var subTotal: Float = 0f)


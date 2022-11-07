package data

data class Cart(var cartItems: MutableList<Item> = mutableListOf(), var subTotal: Float = 0f)


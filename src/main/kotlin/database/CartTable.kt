package database

import data.Cart

object CartTable {

    val carts: MutableMap<String, Cart> = mutableMapOf() // cartId, Cart

    private var cartId = 1

    fun generateCartId(): String {
        return "CartId${cartId++}"
    }

}
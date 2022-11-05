package database

import data.Cart

object CartDatabase {

    val carts: MutableMap<String, Cart> = mutableMapOf() // userId, Cart

    private var cartId = 1

    fun getCartId(): String {
        return "CartId${cartId++}"
    }

}
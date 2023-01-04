package interfaces

import data.*

interface CartDao {

    fun createAndGetCartId(userId: String): String

    fun retrieveCartItems(cartId: String): MutableList<Triple<CartItem, ProductSku, Filters.StatusFilters>>

    fun addToCart(cartId: String, skuId: String)

    fun removeFromCart(cartId: String, skuId: String)

    fun clearCart(cartId: String)

    fun changeItemQuantity(cartId: String, skuId: String, quantity: Int)

    fun updateSubtotal(cartId: String, subTotal: Float)

    fun getCartItemQuantity(cartId: String, skuId: String): Int

}
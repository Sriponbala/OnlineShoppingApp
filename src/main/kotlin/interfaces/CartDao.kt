package interfaces

import data.Item
import data.Product

interface CartDao {

    fun createAndGetCartId(): String

    fun retrieveCartId(userId: String): String

    fun retrieveCartItems(cartId: String): List<Item>

    fun addToCart(cartId: String, product: Product)

    fun removeFromCart(cartId: String, item: Item)

    fun clearCart(cartId: String, cartItems: MutableList<Item>)

    fun retrieveCartItem(cartId: String, productId: String): Item

    fun changeItemQuantityAndPrice(cartId: String, item: Item, quantity: Int)

    fun updateSubtotal(cartId: String, subTotal: Float)

    fun updateAvailableQuantityAndStatusOfCartItems()
}
package utils

import data.Cart
import data.Item
import data.Product
import database.CartDatabase
import database.UsersDatabase

class CartData {

    private lateinit var cartId: String
    private fun createCart() {
        cartId = CartDatabase.generateCartId()
        CartDatabase.carts[cartId] = Cart()
    }

    fun createAndGetCartId(): String {
        createCart()
        return cartId
    }

    fun retrieveCartId(userId: String): String {
        return UsersDatabase.usersAccountInfo[userId]!!.cartId
    }

    fun retrieveCartItems(cartId: String): List<Item> {
        return CartDatabase.carts[cartId]!!.cartItems
    }

    fun addToCart(cartId: String, product: Product) {
        CartDatabase.carts[cartId]!!.cartItems.add(Item(productId = product.productId, productName = product.productName, productPrice = product.price, totalPrice = product.price, category = product.category,1, product.status))
    }

    fun removeFromCart(cartId: String, item: Item) {
        CartDatabase.carts[cartId]!!.cartItems.remove(item)
    }

    fun clearCart(cartId: String, cartItems: MutableList<Item>) {
        CartDatabase.carts[cartId]!!.cartItems.removeAll(cartItems)
    }

    fun retrieveCartItem(cartId: String, productId: String): Item {
        lateinit var cartItem: Item
        for(item in CartDatabase.carts[cartId]!!.cartItems) {
            if(item.productId == productId) {
                cartItem = item
                break
            }
        }
        return cartItem
    }

}


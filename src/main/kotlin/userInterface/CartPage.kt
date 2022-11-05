package userInterface

import backend.CartActivities

class CartPage {

    private val cartActivities = CartActivities()
    private lateinit var cartId: String
    private lateinit var userId: String
    private var isCartEmpty = false

    fun setUserIdAndCartId(userId: String) {
        this.userId = userId
        this.cartId = cartActivities.getCartId(userId)
    }

    fun openCartPage() {

        isCartEmpty = checkIfCartIsEmpty(userId, cartId)
        displayCartItems(cartId)
    }

    private fun displayCartItems(cartId: String) {
        if(isCartEmpty) {
            println("No items found in cart!")
        } else {
            cartActivities.getCartItems(userId, cartId)?.let {
                it.forEachIndexed { index, item ->
                    println("${index + 1}. $item")
                }
            }
        }
    }

    private fun checkIfCartIsEmpty(userId: String, cartId: String): Boolean {
        return cartActivities.getCartItems(userId, cartId)?.isEmpty() == true
    }
}
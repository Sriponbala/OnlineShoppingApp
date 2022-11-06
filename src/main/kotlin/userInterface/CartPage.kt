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

        isCartEmpty = checkIfCartIsEmpty(cartId)
        displayCartItems(cartId)
    }

    private fun displayCartItems(cartId: String) {
        if(isCartEmpty) {
            println("No items found in cart!")
        } else {
            cartActivities.getCartItems(cartId)?.let {
                it.forEachIndexed { index, item ->
                    println("${index + 1}. $item")
                }
            }
        }
    }

    private fun checkIfCartIsEmpty(cartId: String): Boolean {
        return cartActivities.getCartItems(cartId)?.isEmpty() == true
    }
}
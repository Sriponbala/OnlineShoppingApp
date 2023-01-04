package backend

import data.CartItem
import data.Filters
import data.ProductSku
import interfaces.CartDao
import interfaces.UtilityDao

class CartActivities(private val utility: UtilityDao, private val cartDao: CartDao, private val productActivities: ProductActivities) {

    fun createAndGetCartId(userId: String): String {
        return cartDao.createAndGetCartId(userId)
    }

    fun getCartItems(cartId: String): MutableList<Triple<CartItem, ProductSku, Filters.StatusFilters>> {
        return if(utility.checkIfCartExists(cartId)) {
            cartDao.retrieveCartItems(cartId)
        } else mutableListOf()
    }

    fun addToCart(cartId: String, skuId: String): Boolean {
        val itemAddedToCart: Boolean = if(utility.checkIfCartExists(cartId)) {
            if(!utility.checkIfItemIsInCart(cartId, skuId)) {
                cartDao.addToCart(cartId, skuId)
                true
            } else false
        } else false
        return itemAddedToCart
    }

    fun removeFromCart(cartId: String, skuId: String): Boolean {
        val itemRemovedFromCart: Boolean = if(utility.checkIfCartExists(cartId)) {
            if(utility.checkIfItemIsInCart(cartId, skuId)) {
                cartDao.removeFromCart(cartId, skuId)
                true
            } else false
        } else false
        return itemRemovedFromCart
    }

    fun removeFromCart(cartId: String, skuId: String, quantity: Int): Boolean {
        val isItemRemoved: Boolean = if(utility.checkIfCartExists(cartId)) {
            if(utility.checkIfItemIsInCart(cartId,skuId)) {
                if(quantity >= cartDao.getCartItemQuantity(cartId, skuId)) {
                    removeFromCart(cartId, skuId)
                    true
                } else {
                    cartDao.changeItemQuantity(cartId, skuId, quantity)
                    true
                }
            } else false
        } else false
        return isItemRemoved
    }

    fun checkIfCartIsEmpty(cartId: String): Boolean {
        return utility.checkIfCartIsEmpty(cartId)
    }

    fun calculateAndUpdateSubtotal(cartId: String, cartItems: MutableList<Triple<CartItem, ProductSku, Filters.StatusFilters>>): Float {
        var subTotal = 0f
        if(utility.checkIfCartExists(cartId)) {
            for(cartItem in cartItems) {
                if(cartItem.third == Filters.StatusFilters.InStock()) {
                    subTotal += (cartItem.second.price * cartItem.first.quantity)
                }
            }
            cartDao.updateSubtotal(cartId, subTotal)
        }
        return subTotal
    }

    fun getAvailableQuantityOfProduct(skuId: String): Int {
        return productActivities.getAvailableQuantityOfProduct(skuId)
    }

    fun changeQuantityOfCartItem(cartId: String, skuId: String, quantity: Int): Boolean {
        return if(utility.checkIfCartExists(cartId)) {
            if(utility.checkIfItemIsInCart(cartId, skuId)) {
                cartDao.changeItemQuantity(cartId , skuId, quantity)
                calculateAndUpdateSubtotal(cartId, cartDao.retrieveCartItems(cartId))
                true
            } else false
        } else false
    }

}

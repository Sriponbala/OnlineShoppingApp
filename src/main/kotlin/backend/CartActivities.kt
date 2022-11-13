package backend

import data.Item
import enums.ProductStatus
import interfaces.CartDao
import interfaces.UtilityDao
import utils.CartData

class CartActivities(private val utility: UtilityDao) {

    private val cartDao: CartDao = CartData()
    private val productActivities = ProductActivities(utility)

    fun createAndGetCartId(userId: String): String {
        var cartId = ""
        if(utility.checkIfUserExists(userId)) {
            cartId = cartDao.createAndGetCartId()
        }
        return cartId
    }

    fun getCartItems(cartId: String): List<Item> {
        return if(utility.checkIfCartExists(cartId)) {
            cartDao.retrieveCartItems(cartId)
        } else listOf()
    }

    fun addToCart(cartId: String, category: String, productId: String): Boolean {
        val itemAddedToCart: Boolean = if(utility.checkIfCategoryExistsInProductDB(category)) {
            if(utility.checkIfProductExists(productId, category)) {
                if(productActivities.retrieveProductAvailabilityStatus(category, productId) == ProductStatus.IN_STOCK) {
                    if(utility.checkIfCartExists(cartId)) {
                        if(!utility.checkIfItemIsInCart(cartId, productId)) {
                            val product = productActivities.getProductFromDb(productId, category)
                            cartDao.addToCart(cartId, product)
                            true
                        } else false
                    } else false
                } else false
            } else false
        } else false
        return itemAddedToCart
    }

    fun removeFromCart(cartId: String, productId: String, remove: Boolean): Boolean {
        val itemRemovedFromCart: Boolean = if(utility.checkIfCartExists(cartId)) {
            if(remove) {
                if(utility.checkIfItemIsInCart(cartId, productId)) {
                    val item = cartDao.retrieveCartItem(cartId, productId)
                    cartDao.removeFromCart(cartId, item)
                    true
                } else false
            } else false
        } else false
        return itemRemovedFromCart
    }

    fun clearCartItems(cartId: String, cartItems: ArrayList<Item>, remove: Boolean): Boolean {
        val areItemsRemoved = if(utility.checkIfCartExists(cartId)) {
            if(remove) {
                if(cartItems.isNotEmpty()) {
                    cartDao.clearCart(cartId, cartItems)
                    true
                } else false
            } else false
        } else false
        return areItemsRemoved
    }

    fun calculateAndUpdateSubtotal(cartId: String, cartItems: List<Item>): Float {
        var subTotal = 0f
        if(utility.checkIfCartExists(cartId)) {
            for(item in cartItems) {
                if(item.status == ProductStatus.IN_STOCK) {
                    subTotal += (item.totalPrice)
                }
            }
            cartDao.updateSubtotal(cartId, subTotal)
        }
        return subTotal
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {
        return productActivities.getAvailableQuantityOfProduct(productId, category)
    }

    fun changeQuantityAndUpdateTotalPriceOfItem(cartId: String, item: Item, quantity: Int): Boolean {
        return if(utility.checkIfCartExists(cartId)) {
            if(utility.checkIfItemIsInCart(cartId, item.productId)) {
                cartDao.changeItemQuantityAndPrice(cartId ,item, quantity)
                calculateAndUpdateSubtotal(cartId, cartDao.retrieveCartItems(cartId))
                true
            } else false
        } else false
    }

    fun updateAvailabilityStatusOfCartItems() {
       cartDao.updateAvailableQuantityAndStatusOfCartItems()
    }

}

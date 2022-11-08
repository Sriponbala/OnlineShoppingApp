package backend

import data.Item
import database.CartDatabase
import enums.ProductStatus
import utils.CartData
import utils.ProductsData
import utils.Utility

class CartActivities {

    private val cartData = CartData()
    private val utility = Utility()
    private val shopActivities = ShopActivities()

    fun createAndGetCartId(userId: String): String {
        var cartId = ""
        if(utility.checkIfUserExists(userId)) {
            cartId = cartData.createAndGetCartId()
        }
        return cartId
    }

    fun getCartId(userId: String): String {
        val cartId = if(utility.checkIfUserExists(userId)) {
            if(utility.checkIfUserAccountInfoExists(userId)) {
                cartData.retrieveCartId(userId)
            } else ""
        } else ""
        return cartId
    }

    fun getCartItems(cartId: String): List<Item> {
        return if(utility.checkIfCartExists(cartId)) {
            cartData.retrieveCartItems(cartId)
        } else listOf()
    }

    fun addToCart(cartId: String, category: String, productId: String): Boolean {
        val itemAddedToCart: Boolean = if(utility.checkIfCategoryExistsInProductDB(category)) {
            if(utility.checkIfProductExists(productId, category)) {
                if(shopActivities.retrieveProductAvailabilityStatus(category, productId) == ProductStatus.IN_STOCK) {
                    if(utility.checkIfCartExists(cartId)) {
                        if(!utility.checkIfItemIsInCart(cartId, productId)) {
                            val product = shopActivities.getProductFromDb(productId, category)
                            cartData.addToCart(cartId, product)
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
                    val item = cartData.retrieveCartItem(cartId, productId)
                    cartData.removeFromCart(cartId, item)
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
                    cartData.clearCart(cartId, cartItems)
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
            cartData.updateSubtotal(cartId, subTotal)
        }
        return subTotal
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {
        return shopActivities.getAvailableQuantityOfProduct(productId, category)
    }

    fun changeQuantityAndUpdateTotalPriceOfItem(cartId: String, item: Item, quantity: Int): Boolean {
        return if(utility.checkIfCartExists(cartId)) {
            if(utility.checkIfItemIsInCart(cartId, item.productId)) {
                cartData.changeItemQuantityAndPrice(cartId ,item, quantity)
                calculateAndUpdateSubtotal(cartId, cartData.retrieveCartItems(cartId))
                true
            } else false
        } else false
    }

    fun updateAvailabilityStatusOfCartItems() {
       cartData.updateAvailableQuantityAndStatusOfCartItems()
    }

}

// add to cart
/*
//        val status = productsData.retrieveProductAvailabilityStatus(category, productId)
//        itemAddedToCart = if(status == ProductStatus.OUT_OF_STOCK) {
//            false
//        } else {
//            if(isItemInCart(cartId, productId)) {
//                false
//            } else {
//
//                cartData.addToCart(cartId, category, productId, status)
//                true
//            }
//        }
 */

// delete or remove from cart
/*

//        val status = productsData.retrieveProductAvailabilityStatus(category, productId)
//            itemRemovedFromCart = if(status == ProductStatus.OUT_OF_STOCK) {
//                if(isItemInCart(cartId, productId)) {
//                    cartData.removeFromCart(cartId, productId)
//                } else {
//                    false
//                }
//            } else {
//                if(isItemInCart(cartId, productId) && remove) {
//                    cartData.removeFromCart(cartId, productId)
//                } else {
//                    false
//                }
//            }
 */
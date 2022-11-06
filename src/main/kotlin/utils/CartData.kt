package utils

import data.Cart
import data.Item
import database.CartDatabase
import database.ProductsDatabase
import database.UsersDatabase
import enums.ProductStatus

class CartData {

    private lateinit var cartId: String
    private fun createCart() {
        cartId = CartDatabase.generateCartId()
        CartDatabase.carts[cartId] = Cart()
    }

    fun addAndGetCartId(userId: String): String {
        var id = ""
        if(UsersDatabase.users.containsKey(userId)) {
            createCart()
            id = cartId
        }
        return id
    }

    fun retrieveCartId(userId: String): String? {
        var id: String? = null
        if(UsersDatabase.users.containsKey(userId)) {
            if(UsersDatabase.usersAccountInfo.containsKey(userId)) {
                id = UsersDatabase.usersAccountInfo[userId]?.cartId
            }
        }
        return id
    }

    fun addToCart(cartId: String, category: String, productId: String, status: ProductStatus): Boolean {
        var isItemAddedToCart = false
        if(ProductsDatabase.products.containsKey(category)) {
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    CartDatabase.carts[cartId]?.cartItems?.add(Item(productId = productId, productName = product.productName, price = product.price, 1, status))
                    //CartDatabase.carts[userId]?.cartItems?.add(Item(productId = productId, productName = product.productName, price = product.price, 1, status))
                    isItemAddedToCart = true
                    break
                }
            }
        }
        return isItemAddedToCart
    }

    fun retrieveCartItems(cartId: String): List<Item>? {
        var cartItems: List<Item>? = null
        if(CartDatabase.carts.containsKey(cartId)) {
            cartItems = CartDatabase.carts[cartId]?.cartItems
        }
        return cartItems
    }

    fun checkIfItemIsInCart(cartId: String, productId: String): Boolean {
        var isProductInWishList = false
        if(CartDatabase.carts.containsKey(cartId)) {
            for(item in CartDatabase.carts[cartId]?.cartItems!!) {
                if(item.productId == productId) {
                    isProductInWishList = true
                    break
                }
            }
        }
        return isProductInWishList
    }
}


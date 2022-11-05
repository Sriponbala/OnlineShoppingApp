package utils

import data.Cart
import data.Item
import database.CartDatabase
import database.ProductsDatabase
import database.UsersDatabase
import enums.ProductStatus

class CartData {

    fun createCart(userId: String) {
        CartDatabase.carts[userId] = Cart(CartDatabase.getCartId(), mutableListOf(), 0f)
    }

    fun retrieveCartId(userId: String): String {
        return if(CartDatabase.carts.containsKey(userId)) {
            CartDatabase.carts[userId]?.cartId as String
        } else {
            ""
        }
    }

    fun addToCart(userId: String, category: String, productId: String, status: ProductStatus): Boolean {
        var isItemAddedToCart = false
        if(ProductsDatabase.products.containsKey(category)) {
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    CartDatabase.carts[userId]?.cartItems?.add(Item(productId = productId, productName = product.productName, price = product.price, 1, status))
                    isItemAddedToCart = true
                    break
                }
            }
        }
        return isItemAddedToCart
    }

    fun retrieveCartItems(userId: String, cartId: String): List<Item>? {
        var cartItems: List<Item>? = null
        if(CartDatabase.carts.containsKey(userId)) {
            if(CartDatabase.carts[userId]?.cartId == cartId) {
                cartItems = CartDatabase.carts[userId]?.cartItems
            }
        }
        return cartItems
    }
}


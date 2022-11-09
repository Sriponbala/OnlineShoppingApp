package data

import enums.ProductStatus

data class Item(val productId: String, val productName: String, val productPrice: Float, var totalPrice: Float, val category: String, var quantity: Int, var status: ProductStatus)

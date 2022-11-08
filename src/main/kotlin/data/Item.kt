package data

import enums.ProductStatus

data class Item(val productId: String, val productName: String, val productPrice: Float, var totalPrice: Float, val category: String, var quantity: Int, var status: ProductStatus) {

    override fun toString(): String {
        return """Item Name     : $productName
                 |Item price    : $productPrice
                 |Quantity      : $quantity
                 |Total Price   : $totalPrice
                 |Status        : $status
        """.trimMargin()
    }
}

package data

import enums.ProductStatus

data class Item(val productId: String, val productName: String, val price: Float, var quantity: Int, var status: ProductStatus) {

    override fun toString(): String {
        return """Product Name  : $productName
                 |Price         : $price
                 |Quantity      : $quantity
                 |Status        : $status
        """.trimMargin()
    }
}

package data

import enums.*

sealed class ProductSku {

    abstract val skuId: String
    abstract val productName: String
    abstract val price: Float
    abstract val category: ProductCategory

    data class Book(override val skuId: String, override val productName: String, override val price: Float, val bookType: BookType): ProductSku() {
        override val category: ProductCategory = ProductCategory.BOOK
    }

    data class Mobile(override val skuId: String, override val productName: String, override val price: Float, val brand: Brand): ProductSku() {
        override val category: ProductCategory = ProductCategory.MOBILE
    }

    data class Clothing(override val skuId: String, override val productName: String, override val price: Float, val gender: Gender, val colour: Colour): ProductSku() {
        override val category: ProductCategory = ProductCategory.CLOTHING
    }

    data class Earphone(override val skuId: String, override val productName: String, override val price: Float, val brand: Brand): ProductSku() {
        override val category: ProductCategory = ProductCategory.EARPHONE
    }

}







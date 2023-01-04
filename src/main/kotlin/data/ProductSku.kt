package data

sealed class ProductSku {

    abstract val skuId: String
    abstract val productName: String
    abstract val price: Float
    abstract val category: Filters.CategoryFilters

    data class Book(override val skuId: String, override val productName: String, override val price: Float, val bookType: Filters.BookTypeFilters): ProductSku() {
        override val category: Filters.CategoryFilters = Filters.CategoryFilters.Book()
    }

    data class Mobile(override val skuId: String, override val productName: String, override val price: Float, val brand: Filters.BrandFilters, val storage: Filters.StorageFilters): ProductSku() {
        override val category: Filters.CategoryFilters = Filters.CategoryFilters.Mobile()
    }

    data class Clothing(override val skuId: String, override val productName: String, override val price: Float, val gender: Filters.GenderFilters, val colour: Filters.ColourFilters): ProductSku() {
        override val category: Filters.CategoryFilters = Filters.CategoryFilters.Clothing()
    }

    data class Earphone(override val skuId: String, override val productName: String, override val price: Float, val type: Filters.EarPhoneTypeFilters, val brand: Filters.BrandFilters): ProductSku() {
        override val category: Filters.CategoryFilters = Filters.CategoryFilters.Earphone()
    }

}







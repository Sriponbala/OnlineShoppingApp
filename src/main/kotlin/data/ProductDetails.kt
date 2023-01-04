package data

import utils.Helper

data class ProductDetails(val skuId: String, var status: Filters.StatusFilters) {

    val productId: String = Helper.generateProductId()
}

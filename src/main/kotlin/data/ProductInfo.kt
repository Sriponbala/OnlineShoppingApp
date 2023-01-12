package data

import enums.StockStatus
import utils.Helper

data class ProductInfo(val skuId: String, var status: StockStatus) {
    val productId: String = Helper.generateProductId()
}

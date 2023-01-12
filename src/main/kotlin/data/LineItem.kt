package data

import utils.Helper
import java.time.LocalDate

data class LineItem(val skuId: String, val productId: String, val orderedDate: LocalDate) {
    val lineItemId: String = Helper.generateLineItemId()
}

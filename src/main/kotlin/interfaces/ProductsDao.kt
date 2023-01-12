package interfaces

import data.LineItem
import data.ProductInfo
import data.ProductSku
import enums.StockStatus

interface ProductsDao {

    fun retrieveAllProducts(): MutableList<Pair<ProductSku, StockStatus>>

    fun retrieveAvailableQuantityOfProduct(skuId: String): Int

    fun updateStatusOfProduct(lineItem: LineItem)

    fun retrieveAProduct(skuId: String): ProductInfo

    fun retrieveProductDetails(skuId: String): Pair<ProductSku, StockStatus>

    fun addProductDetails()

    fun getProducts(skuId: String, quantity: Int, lineItems: MutableList<LineItem>): MutableList<ProductInfo>

    fun getProducts(skuId: String, quantity: Int): MutableList<ProductInfo>

}
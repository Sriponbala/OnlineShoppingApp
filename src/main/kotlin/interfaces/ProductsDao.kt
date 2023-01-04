package interfaces

import data.Filters
import data.LineItem
import data.ProductDetails
import data.ProductSku

interface ProductsDao {

    fun retrieveAllProducts(): MutableList<Pair<ProductSku, Filters.StatusFilters>>

    fun retrieveAvailableQuantityOfProduct(skuId: String): Int

    fun updateStatusOfProduct(lineItem: LineItem)

    fun retrieveAProduct(skuId: String): ProductDetails

    fun retrieveProductDetails(skuId: String): Pair<ProductSku, Filters.StatusFilters>

    fun addProductDetails()

    fun getProducts(skuId: String, quantity: Int, lineItems: MutableList<LineItem>): MutableList<ProductDetails>

}
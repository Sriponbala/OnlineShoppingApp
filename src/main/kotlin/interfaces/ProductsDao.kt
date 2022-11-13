package interfaces

import data.Product
import enums.ProductStatus

interface ProductsDao {

    fun retrieveListOfProducts(category: String): MutableList<Product>?

    fun retrieveListOfCategories(): List<String>

    fun updateAvailableQuantityOfProduct(productId: String, category: String, quantity: Int)

    fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus

    fun retrieveAvailableQuantityOfProduct(productId: String, category: String): Int

    fun updateStatusOfProduct(productId: String, category: String, status: ProductStatus)

    fun retrieveProduct(productId: String, category: String): Product

}
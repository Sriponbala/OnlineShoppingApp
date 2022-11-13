package utils

import data.Product
import database.ProductsTable
import enums.ProductStatus
import interfaces.ProductsDao

class ProductsData: ProductsDao {

    override fun retrieveListOfProducts(category: String): MutableList<Product>? {
        return if(ProductsTable.products.containsKey(category)) {
            ProductsTable.products[category]
        } else null
    }

    override fun retrieveListOfCategories(): List<String> {
        return ProductsTable.products.map{
            it.key
        }
    }

    override fun updateAvailableQuantityOfProduct(productId: String, category: String, quantity: Int) {
        retrieveProduct(productId, category).availableQuantity -= quantity
    }

    override fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus {
        return retrieveProduct(productId, category).status
    }

    override fun retrieveAvailableQuantityOfProduct(productId: String, category: String): Int {
        return retrieveProduct(productId, category).availableQuantity
    }

    override fun updateStatusOfProduct(productId: String, category: String, status: ProductStatus) {
        retrieveProduct(productId, category).status = status
    }

    override fun retrieveProduct(productId: String, category: String): Product {
        lateinit var productData: Product
        for(product in ProductsTable.products[category]!!) {
            if(product.productId == productId) {
                productData = product
                break
            }
        }
        return productData
    }

}


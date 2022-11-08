package utils

import data.Product
import database.ProductsDatabase
import enums.ProductStatus

class ProductsData {

    fun retrieveListOfProducts(category: String): MutableList<Product>? {
        return if(ProductsDatabase.products.containsKey(category)) {
            ProductsDatabase.products[category]
        } else null
    }

    fun retrieveListOfCategories(): List<String> {
        return ProductsDatabase.products.map{
            it.key
        }
    }

    fun updateAvailableQuantityOfProduct(productId: String, category: String, quantity: Int) {
        retrieveProduct(productId, category).availableQuantity -= quantity
    }

    fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus {
        return retrieveProduct(productId, category).status
    }

    fun retrieveAvailableQuantityOfProduct(productId: String, category: String): Int {
        return retrieveProduct(productId, category).availableQuantity
    }

    fun updateStatusOfProduct(productId: String, category: String, status: ProductStatus) {
        retrieveProduct(productId, category).status = status
    }

    fun retrieveProduct(productId: String, category: String): Product {
        lateinit var productData: Product
        for(product in ProductsDatabase.products[category]!!) {
            if(product.productId == productId) {
                productData = product
                break
            }
        }
        return productData
    }

}


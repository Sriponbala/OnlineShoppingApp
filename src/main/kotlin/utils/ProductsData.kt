package utils

import data.Product
import database.ProductsDatabase
import enums.ProductStatus

class ProductsData(private val dbUserName: String = "root",
                   private val dbPassword: String = "tiger") {


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

    fun updateAvailableQuantityOfProduct(productId: String) {

    }

    fun updateRequiredQuantityOfProduct() {

    }

    fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus {
        var status: ProductStatus = ProductStatus.OUT_OF_STOCK
            for(product in ProductsDatabase.products[category]!!) {
                if(product.productId == productId) {
                    if(product.availableQuantity > 0) {
                        status = ProductStatus.IN_STOCK
                        break
                    }
                }
            }
        return status
    }
}
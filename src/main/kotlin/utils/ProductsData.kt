package utils

import data.Product
import database.ProductsDatabase

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

    private fun checkIfOutOfStock() {

    }
}
package backend

import data.Product
import utils.ProductsData

class ShopActivities {

    private val productsData = ProductsData()
    private var productsList: List<Product>? = null


    fun getCategories(): List<String> {
        return productsData.retrieveListOfCategories()
    }

    fun getProductsList(category: String): Map<Int, Triple<String, String, Float>> {
        productsList = productsData.retrieveListOfProducts(category)
        val productsDetails = mutableMapOf<Int, Triple<String, String, Float>>()
        productsList?.forEachIndexed{ index, product ->
            productsDetails[index + 1] = Triple(product.productId, product.productName, product.price)
        }
        return productsDetails
    }

    fun getAProduct(productId: String): Product? {
        var selectedProduct: Product? = null
        for(product in productsList!!) {
            if(product.productId == productId) {
                selectedProduct = product
                break
            }
        }
        return selectedProduct
    }

}
package backend

import data.Product
import enums.ProductStatus
import utils.ProductsData
import utils.Utility

class ShopActivities {

    private val productsData = ProductsData()
    private var productsList: List<Product>? = null
    private val utility = Utility()

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

    fun getProductFromDb(productId: String, category: String): Product {
        return productsData.retrieveProduct(productId, category)
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {
        return if(utility.checkIfProductExists(productId, category)) {
            productsData.retrieveAvailableQuantityOfProduct(productId, category)
        } else 0
    }

    fun updateAvailableQuantityAndStatusOfProduct(productId: String, category: String, quantity: Int) {
        if(utility.checkIfProductExists(productId, category)) {
            if((productsData.retrieveAvailableQuantityOfProduct(productId, category) - quantity) >= 0 ) {
                productsData.updateAvailableQuantityOfProduct(productId, category, quantity)
                if(productsData.retrieveAvailableQuantityOfProduct(productId, category) == 0) {
                    productsData.updateStatusOfProduct(productId, category, ProductStatus.OUT_OF_STOCK)
                } else {
                    productsData.updateStatusOfProduct(productId, category, ProductStatus.IN_STOCK)
                }
            }
        }
    }

    fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus {
        return productsData.retrieveProductAvailabilityStatus(category, productId)
    }

}
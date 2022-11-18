package backend

import data.Product
import enums.ProductStatus
import interfaces.ProductsDao
import interfaces.UtilityDao

class ProductActivities(private val utility: UtilityDao, private val productsDao: ProductsDao) {

    private var productsList: List<Product>? = null

    fun getCategories(): List<String> {

        return productsDao.retrieveListOfCategories()
    }

    fun getProductsList(category: String): Map<Int, Triple<String, String, Float>> {

        productsList = productsDao.retrieveListOfProducts(category)
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

        return productsDao.retrieveProduct(productId, category)
    }

    fun getAvailableQuantityOfProduct(productId: String, category: String): Int {

        return if(utility.checkIfProductExists(productId, category)) {
            productsDao.retrieveAvailableQuantityOfProduct(productId, category)
        } else 0
    }

    fun updateAvailableQuantityAndStatusOfProduct(productId: String, category: String, quantity: Int) {

        if(utility.checkIfProductExists(productId, category)) {
            if((productsDao.retrieveAvailableQuantityOfProduct(productId, category) - quantity) >= 0 ) {
                productsDao.updateAvailableQuantityOfProduct(productId, category, quantity)
                if(productsDao.retrieveAvailableQuantityOfProduct(productId, category) == 0) {
                    productsDao.updateStatusOfProduct(productId, category, ProductStatus.OUT_OF_STOCK)
                } else {
                    productsDao.updateStatusOfProduct(productId, category, ProductStatus.IN_STOCK)
                }
            }
        }
    }

    fun retrieveProductAvailabilityStatus(category: String, productId: String): ProductStatus {

        return productsDao.retrieveProductAvailabilityStatus(category, productId)
    }

}
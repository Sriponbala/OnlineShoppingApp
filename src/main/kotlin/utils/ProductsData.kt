package utils

import data.Filters
import data.LineItem
import data.ProductDetails
import data.ProductSku
import database.*
import interfaces.ProductsDao

class ProductsData(private val userName: String = "root",
                   private val password: String = "tiger"): ProductsDao {

    private val database: Database = Database.getConnection(this.userName, this.password)!!

    override fun retrieveAllProducts(): MutableList<Pair<ProductSku, Filters.StatusFilters>> {
        val productsList: MutableList<Pair<ProductSku, Filters.StatusFilters>> = mutableListOf()
        for(productSku in database.productsSku) {
            if(productsList.isEmpty()) {
                var status: Filters.StatusFilters = Filters.StatusFilters.OutOfStock()
                for(productDetails in database.productsDetails) {
                    if(productSku.skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                        status = Filters.StatusFilters.InStock()
                        break
                    }
                }
                val productDetails = Pair(productSku, status)
                productsList.add(productDetails)
            } else {
                var isProductAlreadyEntered = false
                for(product in productsList) {
                    if(product.first.skuId == productSku.skuId) {
                        isProductAlreadyEntered = true
                        break
                    }
                }
                if(!isProductAlreadyEntered) {
                    var status: Filters.StatusFilters = Filters.StatusFilters.OutOfStock()
                    for(productDetails in database.productsDetails) {
                        if(productSku.skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                            status = Filters.StatusFilters.InStock()
                            break
                        }
                    }
                    val productDetails = Pair(productSku, status)
                    productsList.add(productDetails)
                }
            }
        }
        return productsList
    }

    override fun retrieveAvailableQuantityOfProduct(skuId: String): Int {
        var availableQuantity = 0
        for(productDetails in database.productsDetails) {
            if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                availableQuantity += 1
            }
        }
        return availableQuantity
    }

    override fun updateStatusOfProduct(lineItem: LineItem) {
        for(productDetails in database.productsDetails) {
            if(lineItem.skuId == productDetails.skuId && lineItem.productId == productDetails.productId) {
                productDetails.status = Filters.StatusFilters.OutOfStock()
                updateAvailableQuantityOfProduct(lineItem.skuId)
            }
        }
    }

    override fun retrieveAProduct(skuId: String): ProductDetails {
        lateinit var productData: ProductDetails
        for(productDetails in database.productsDetails) {
            if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                productData = productDetails
                break
            }
        }
        return productData
    }

    override fun retrieveProductDetails(skuId: String): Pair<ProductSku, Filters.StatusFilters> {
        var product: Pair<ProductSku, Filters.StatusFilters>? = null
        for(localProductSku in database.productsSku) {
            if(skuId == localProductSku.skuId) {
                for(productDetails in database.productsDetails) {
                    if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                        product = Pair(localProductSku, Filters.StatusFilters.InStock())
                        break
                    } else {
                        product = Pair(localProductSku, Filters.StatusFilters.OutOfStock())
                    }
                }
                break
            }
        }
        return product!!
    }

    override fun addProductDetails() {
        var count = 0
        for (productSku in database.productsSku) {
            val availableQuantity = getAvailableQuantityOfProduct(productSku.skuId)
            while(count < availableQuantity) {
                val productDetails = ProductDetails(productSku.skuId, Filters.StatusFilters.InStock())
                database.productsDetails.add(productDetails)
                count++
            }
            count = 0
        }
    }

    private fun getAvailableQuantityOfProduct(skuId: String): Int {
        var availableQuantity = 0
        for(stock in database.stocks) {
            if(skuId == stock.skuId) {
                availableQuantity = stock.availableQuantity
                break
            }
        }
        return availableQuantity
    }

    private fun updateAvailableQuantityOfProduct(skuId: String) {
        for(stock in database.stocks) {
            if(skuId == stock.skuId) {
                stock.availableQuantity -= 1
                break
            }
        }
    }

    override fun getProducts(skuId: String, quantity: Int): MutableList<ProductDetails> {
        val products = mutableListOf<ProductDetails>()
        var count = 0
        for(productDetails in database.productsDetails) {
            var present = false
            if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                if(count == quantity) {
                    break
                } else {
                    if(products.isEmpty()) {
                        products.add(productDetails)
                        count++
                    } else {
                        for(product in products) {
                            if(productDetails.productId == product.productId) {
                                present = true
                                break
                            }
                        }
                        if(!present) {
                            products.add(productDetails)
                            count++
                        }
                    }
                }
            }
        }
        return products
    }

    override fun getProducts(skuId: String, quantity: Int, lineItems: MutableList<LineItem>): MutableList<ProductDetails> {
        val products = mutableListOf<ProductDetails>()
        var count = 0
            for(productDetails in database.productsDetails) {
                var present = false
                if(lineItems.isEmpty()) {
                    if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                        products.add(productDetails)
                        count++
                    }
                }
                if(count == quantity) {
                    break
                } else {
                    if(skuId == productDetails.skuId && productDetails.status == Filters.StatusFilters.InStock()) {
                        for(lineItem in lineItems) {
                            if(lineItem.productId == productDetails.productId) {
                                present = true
                                break
                            }
                        }
                        if(!present) {
                            if(products.isEmpty()) {
                                products.add(productDetails)
                                count++
                            } else {
                                for(product in products) {
                                    if(product.productId != productDetails.productId) {
                                        products.add(productDetails)
                                        count++
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return products
    }

}


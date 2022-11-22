package backend

import data.Product
import enums.ProductStatus
import interfaces.ProductsDao
import interfaces.UtilityDao

class ProductActivities(private val utility: UtilityDao, private val productsDao: ProductsDao) {

    private lateinit var productsList: List<Product>
    private val filterOptions: MutableList<String> = mutableListOf()
    private val uniqueFilters: MutableList<String> = mutableListOf()
    private lateinit var filteredList: List<Product>
    private lateinit var list: MutableList<Product>

    fun getCategories(): List<String> {
        return productsDao.retrieveListOfCategories()
    }

    fun getProductsList(category: String): Map<Int, Triple<String, String, Float>> {
        productsList = productsDao.retrieveListOfProducts(category)!!
        val productsDetails = mutableMapOf<Int, Triple<String, String, Float>>()
        productsList.forEachIndexed{ index, product ->
            productsDetails[index + 1] = Triple(product.productId, product.productName, product.price)
        }
        return productsDetails
    }

    fun getAProduct(productId: String): Product? {
        var selectedProduct: Product? = null
        for(product in productsList) {
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

    fun getFilterOptions(category: String): List<String> {
        if (utility.checkIfCategoryExistsInProductDB(category)) {
            when(category) {
                "book" -> {
                    filterOptions.clear()
                    filterOptions.add("bookType")
                }
                "mobile" -> {
                    filterOptions.clear()
                    filterOptions.add("brand")
                    filterOptions.add("storage")
                }
                "clothing" -> {
                    filterOptions.clear()
                    filterOptions.add("gender")
                    filterOptions.add("colour")
                }
                "earphone" -> {
                    filterOptions.clear()
                    filterOptions.add("brand")
                    filterOptions.add("colour")
                    filterOptions.add("type")
                }
            }
        }
        return filterOptions
    }

    fun getUniqueFilters(category: String, filterOption: String): List<String> {
        if(utility.checkIfCategoryExistsInProductDB(category)) {
            list = productsDao.retrieveListOfProducts(category)!!
            when(category) {
                "book" -> {
                    when(filterOption) {
                        "bookType" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("fiction")
                            uniqueFilters.add("nonfiction")
                        }
                    }
                }
                "mobile" -> {
                    when(filterOption) {
                        "brand" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("Apple")
                            uniqueFilters.add("Samsung")
                        }
                        "storage" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("128 GB")
                            uniqueFilters.add("64 GB")
                        }
                    }
                }
                "clothing" -> {
                    when(filterOption) {
                        "gender" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("male")
                            uniqueFilters.add("female")
                        }
                        "colour" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("blue")
                            uniqueFilters.add("red")
                            uniqueFilters.add("black")
                        }
                    }
                }
                "earphone" -> {
                    when(filterOption) {
                        "brand" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("boAt")
                            uniqueFilters.add("zebronics")
                            uniqueFilters.add("generic")
                        }
                        "colour" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("black")
                            uniqueFilters.add("red")
                        }
                        "type" -> {
                            uniqueFilters.clear()
                            uniqueFilters.add("wired")
                            uniqueFilters.add("wireless")
                        }
                    }
                }
            }
        }
        return uniqueFilters
    }

    fun getFilteredList(category: String, filterOption: String, value: String) {
        if(utility.checkIfCategoryExistsInProductDB(category)) {
            when(category) {
                "book" -> {
                    when(filterOption) {
                        "bookType" -> {
                            when(value) {
                                "fiction" -> {
                                    filteredList = list.filter { it is Product.Book && it.bookType == "fiction" }
                                }
                                "nonfiction" -> {
                                    filteredList = list.filter { it is Product.Book && it.bookType == "nonfiction" }
                                }
                            }
                        }
                    }
                }
                "mobile" -> {
                    when(filterOption) {
                        "brand" -> {
                            when(value) {
                                "Apple" -> {
                                    filteredList = list.filter { it is Product.Mobile && it.brand == "Apple" }
                                }
                                "Samsung" -> {
                                    filteredList = list.filter { it is Product.Mobile && it.brand == "Samsung" }
                                }
                            }
                        }
                        "storage" -> {
                            when(value) {
                                "128 GB" -> {
                                    filteredList = list.filter { it is Product.Mobile && it.storage == "128 GB" }
                                }
                                "64 GB" -> {
                                    filteredList = list.filter { it is Product.Mobile && it.storage == "64 GB" }
                                }
                            }
                        }
                    }

                }
                "clothing" -> {
                    when(filterOption) {
                        "gender" -> {
                            when(value) {
                                "male" -> {
                                    filteredList = list.filter { it is Product.Clothing && it.gender == "male" }
                                }
                                "female" -> {
                                    filteredList = list.filter { it is Product.Clothing && it.gender == "female" }
                                }
                            }
                        }
                        "colour" -> {
                            when(value) {
                                "blue" -> {
                                    filteredList = list.filter { it is Product.Clothing && it.colour == "blue" }
                                }
                                "red" -> {
                                    filteredList = list.filter { it is Product.Clothing && it.colour == "red" }
                                }
                                "black" -> {
                                    filteredList = list.filter { it is Product.Clothing && it.colour == "black" }
                                }
                            }
                        }
                    }
                }
                "earphone" -> {
                    when(filterOption) {
                        "brand" -> {
                            when(value) {
                                "boAt" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.brand == "boAt" }
                                }
                                "zebronics" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.brand == "zebronics" }
                                }
                                "generic" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.brand == "generic" }
                                }
                            }
                        }
                        "type" -> {
                            when(value) {
                                "wired" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.type == "wired" }
                                }
                                "wireless" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.type == "wireless" }
                                }
                            }
                        }
                        "colour" -> {
                            when(value) {
                                "red" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.colour == "red" }
                                }
                                "black" -> {
                                    filteredList = list.filter { it is Product.Earphone && it.colour == "black" }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun getProductsList(): Map<Int, Triple<String, String, Float>> {
        val productsDetails = mutableMapOf<Int, Triple<String, String, Float>>()
        filteredList.forEachIndexed{ index, product ->
            productsDetails[index + 1] = Triple(product.productId, product.productName, product.price)
        }
        return productsDetails
    }

}
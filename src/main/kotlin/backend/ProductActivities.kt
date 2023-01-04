package backend

import data.Filters
import data.LineItem
import data.ProductDetails
import data.ProductSku
import enums.*
import interfaces.ProductsDao
import interfaces.UtilityDao

class ProductActivities(private val utility: UtilityDao, private val productsDao: ProductsDao) {

    private lateinit var productsList: MutableList<Pair<ProductSku, Filters.StatusFilters>>
    private lateinit var filteredList: List<Pair<ProductSku, Filters.StatusFilters>>

    fun getProductsList(): MutableList<Pair<ProductSku, Filters.StatusFilters>> {
        productsList = productsDao.retrieveAllProducts()
        return productsList
    }

    fun getProductsList(category: ProductCategories): List<Pair<ProductSku, Filters.StatusFilters>> {
        return when(category) {
            ProductCategories.BOOK -> productsList.filter { it.first.category == Filters.CategoryFilters.Book() }
            ProductCategories.MOBILE -> productsList.filter { it.first.category == Filters.CategoryFilters.Mobile() }
            ProductCategories.CLOTHING -> productsList.filter { it.first.category == Filters.CategoryFilters.Clothing() }
            ProductCategories.EARPHONE -> productsList.filter { it.first.category == Filters.CategoryFilters.Earphone() }
        }
    }

    fun getProductDetails(skuId: String): Pair<ProductSku, Filters.StatusFilters> {
        return productsDao.retrieveProductDetails(skuId)
    }

    fun getProducts(skuId: String, quantity: Int, lineItems: MutableList<LineItem>): MutableList<ProductDetails> {
        return productsDao.getProducts(skuId, quantity, lineItems)
    }

    fun getAvailableQuantityOfProduct(skuId: String): Int {
        return if(utility.checkIfProductExists(skuId)) {
            productsDao.retrieveAvailableQuantityOfProduct(skuId)
        } else 0
    }

    fun getFilteredList(category: ProductCategories, filterOption: FilterOptions, finalFilter: Any): List<Pair<ProductSku, Filters.StatusFilters>> {
        filteredList = when(category) {
            ProductCategories.BOOK -> {
                when(filterOption) {
                    FilterOptions.PRICE -> {
                        when(finalFilter as PriceFilters) {
                            PriceFilters.BELOW_100 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && (it.first.price.toLong() in Filters.PriceFilters.Below_100().start..Filters.PriceFilters.Below_100().end)}
                            }
                            PriceFilters.BETWEEN_100_AND_500 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.Between_100_And_500().start..Filters.PriceFilters.Between_100_And_500().end}
                            }
                            PriceFilters.FROM_500_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.From_500_To_3000().start..Filters.PriceFilters.From_500_To_3000().end}
                            }
                            PriceFilters.ABOVE_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.Above_3000().start..Filters.PriceFilters.Above_3000().end}
                            }
                            PriceFilters.BELOW_15000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.Below_15000().start..Filters.PriceFilters.Below_15000().end}
                            }
                            PriceFilters.FROM_15000_TO_40000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.From_15000_To_40000().start..Filters.PriceFilters.From_15000_To_40000().end}
                            }
                            PriceFilters.FROM_40000_TO_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.From_40000_100000().start..Filters.PriceFilters.From_40000_100000().end}
                            }
                            PriceFilters.ABOVE_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.first.price.toLong() in Filters.PriceFilters.Above_100000().start..Filters.PriceFilters.Above_100000().end}
                            }
                        }
                    }
                    FilterOptions.STATUS -> {
                        when(finalFilter as StatusFilters) {
                            StatusFilters.INSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.second == Filters.StatusFilters.InStock()}
                            }
                            StatusFilters.OUTOFSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Book() && it.second == Filters.StatusFilters.OutOfStock()}
                            }
                        }
                    }
                }
            }

            ProductCategories.MOBILE -> {
                when(filterOption) {
                    FilterOptions.PRICE -> {
                        when(finalFilter as PriceFilters) {
                            PriceFilters.BELOW_100 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && (it.first.price.toLong() in Filters.PriceFilters.Below_100().start..Filters.PriceFilters.Below_100().end)}
                            }
                            PriceFilters.BETWEEN_100_AND_500 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.Between_100_And_500().start..Filters.PriceFilters.Between_100_And_500().end}
                            }
                            PriceFilters.FROM_500_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.From_500_To_3000().start..Filters.PriceFilters.From_500_To_3000().end}
                            }
                            PriceFilters.ABOVE_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.Above_3000().start..Filters.PriceFilters.Above_3000().end}
                            }
                            PriceFilters.BELOW_15000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.Below_15000().start..Filters.PriceFilters.Below_15000().end}
                            }
                            PriceFilters.FROM_15000_TO_40000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.From_15000_To_40000().start..Filters.PriceFilters.From_15000_To_40000().end}
                            }
                            PriceFilters.FROM_40000_TO_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.From_40000_100000().start..Filters.PriceFilters.From_40000_100000().end}
                            }
                            PriceFilters.ABOVE_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.first.price.toLong() in Filters.PriceFilters.Above_100000().start..Filters.PriceFilters.Above_100000().end}
                            }
                        }
                    }
                    FilterOptions.STATUS -> {
                        when(finalFilter as StatusFilters) {
                            StatusFilters.INSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.second == Filters.StatusFilters.InStock()}
                            }
                            StatusFilters.OUTOFSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Mobile() && it.second == Filters.StatusFilters.OutOfStock()}
                            }
                        }
                    }
                }
            }


            ProductCategories.CLOTHING -> {
                when(filterOption) {
                    FilterOptions.PRICE -> {
                        when(finalFilter as PriceFilters) {
                            PriceFilters.BELOW_100 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && (it.first.price.toLong() in Filters.PriceFilters.Below_100().start..Filters.PriceFilters.Below_100().end)}
                            }
                            PriceFilters.BETWEEN_100_AND_500 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.Between_100_And_500().start..Filters.PriceFilters.Between_100_And_500().end}
                            }
                            PriceFilters.FROM_500_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.From_500_To_3000().start..Filters.PriceFilters.From_500_To_3000().end}
                            }
                            PriceFilters.ABOVE_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.Above_3000().start..Filters.PriceFilters.Above_3000().end}
                            }
                            PriceFilters.BELOW_15000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.Below_15000().start..Filters.PriceFilters.Below_15000().end}
                            }
                            PriceFilters.FROM_15000_TO_40000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.From_15000_To_40000().start..Filters.PriceFilters.From_15000_To_40000().end}
                            }
                            PriceFilters.FROM_40000_TO_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.From_40000_100000().start..Filters.PriceFilters.From_40000_100000().end}
                            }
                            PriceFilters.ABOVE_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.first.price.toLong() in Filters.PriceFilters.Above_100000().start..Filters.PriceFilters.Above_100000().end}
                            }
                        }
                    }
                    FilterOptions.STATUS -> {
                        when(finalFilter as StatusFilters) {
                            StatusFilters.INSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.second == Filters.StatusFilters.InStock()}
                            }
                            StatusFilters.OUTOFSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Clothing() && it.second == Filters.StatusFilters.OutOfStock()}
                            }
                        }
                    }
                }
            }
            ProductCategories.EARPHONE -> {
                when(filterOption) {
                    FilterOptions.PRICE -> {
                        when(finalFilter as PriceFilters) {
                            PriceFilters.BELOW_100 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && (it.first.price.toLong() in Filters.PriceFilters.Below_100().start..Filters.PriceFilters.Below_100().end)}
                            }
                            PriceFilters.BETWEEN_100_AND_500 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.Between_100_And_500().start..Filters.PriceFilters.Between_100_And_500().end}
                            }
                            PriceFilters.FROM_500_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.From_500_To_3000().start..Filters.PriceFilters.From_500_To_3000().end}
                            }
                            PriceFilters.ABOVE_3000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.Above_3000().start..Filters.PriceFilters.Above_3000().end}
                            }
                            PriceFilters.BELOW_15000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.Below_15000().start..Filters.PriceFilters.Below_15000().end}
                            }
                            PriceFilters.FROM_15000_TO_40000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.From_15000_To_40000().start..Filters.PriceFilters.From_15000_To_40000().end}
                            }
                            PriceFilters.FROM_40000_TO_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.From_40000_100000().start..Filters.PriceFilters.From_40000_100000().end}
                            }
                            PriceFilters.ABOVE_100000 -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.first.price.toLong() in Filters.PriceFilters.Above_100000().start..Filters.PriceFilters.Above_100000().end}
                            }
                        }
                    }
                    FilterOptions.STATUS -> {
                        when(finalFilter as StatusFilters) {
                            StatusFilters.INSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.second == Filters.StatusFilters.InStock()}
                            }
                            StatusFilters.OUTOFSTOCK -> {
                                productsList.filter {  it.first.category == Filters.CategoryFilters.Earphone() && it.second == Filters.StatusFilters.OutOfStock()}
                            }
                        }
                    }
                }
            }
        }
        return filteredList
    }

    fun addProductDetails() {
        productsDao.addProductDetails()
    }

    fun updateStatusOfProduct(lineItem: LineItem) {
        productsDao.updateStatusOfProduct(lineItem)
    }

}
package backend

import data.Filter
import data.LineItem
import data.ProductInfo
import data.ProductSku
import enums.*
import interfaces.ProductsDao
import interfaces.UtilityDao

class ProductActivities(private val utility: UtilityDao, private val productsDao: ProductsDao) {

    private lateinit var productsList: MutableList<Pair<ProductSku, StockStatus>>
    private lateinit var filteredList: List<Pair<ProductSku, StockStatus>>

    fun getProductsList(): MutableList<Pair<ProductSku, StockStatus>> {
        productsList = productsDao.retrieveAllProducts()
        return productsList
    }

    fun getProductsList(category: ProductCategory): List<Pair<ProductSku, StockStatus>> {
        return productsList.filter { it.first.category == category }
    }

    fun getProductDetails(skuId: String): Pair<ProductSku, StockStatus> {
        return productsDao.retrieveProductDetails(skuId)
    }

    fun getProducts(skuId: String, quantity: Int, lineItems: MutableList<LineItem>): MutableList<ProductInfo> {
        return productsDao.getProducts(skuId, quantity, lineItems)
    }

    fun getProducts(skuId: String, quantity: Int): MutableList<ProductInfo> {
        return productsDao.getProducts(skuId, quantity)
    }

    fun getAvailableQuantityOfProduct(skuId: String): Int {
        return if(utility.checkIfProductExists(skuId)) {
            productsDao.retrieveAvailableQuantityOfProduct(skuId)
        } else 0
    }

    fun getFilteredList(category: ProductCategory, filterOption: FilterBy, finalFilter: Filter): List<Pair<ProductSku, StockStatus>> {
        filteredList = when(category) {
            ProductCategory.BOOK -> {
                when(filterOption) {
                    FilterBy.PRICE -> {
                        val filter : Filter.PriceFilter = finalFilter as Filter.PriceFilter
                        productsList.filter { it.first.category == ProductCategory.BOOK && it.first.price.toLong() in filter.start .. filter.end }
                    }
                    FilterBy.STATUS -> {
                        val filter : Filter.StatusFilter = finalFilter as Filter.StatusFilter
                        productsList.filter { it.first.category == ProductCategory.BOOK && it.second.status == filter.status }
                    }
                    FilterBy.BOOKTYPE -> {
                        val filter : Filter.BookTypeFilter = finalFilter as Filter.BookTypeFilter
                        productsList.filter { it.first.category == ProductCategory.BOOK && (it.first as ProductSku.Book).bookType.type == filter.type }
                    }
                    else -> { emptyList() }
                }
            }
            ProductCategory.MOBILE -> {
                when(filterOption) {
                    FilterBy.PRICE -> {
                        val filter : Filter.PriceFilter = finalFilter as Filter.PriceFilter
                        productsList.filter { it.first.category == ProductCategory.MOBILE && it.first.price.toLong() in filter.start .. filter.end }
                    }
                    FilterBy.STATUS -> {
                        val filter : Filter.StatusFilter = finalFilter as Filter.StatusFilter
                        productsList.filter { it.first.category == ProductCategory.MOBILE && it.second.status == filter.status }
                    }
                    FilterBy.BRAND -> {
                        val filter : Filter.BrandFilter = finalFilter as Filter.BrandFilter
                        productsList.filter { it.first.category == ProductCategory.MOBILE && (it.first as ProductSku.Mobile).brand.brandName == filter.brandName}
                    }
                    else -> { emptyList() }
                }

            }
            ProductCategory.CLOTHING -> {
                when(filterOption) {
                    FilterBy.PRICE -> {
                        val filter : Filter.PriceFilter = finalFilter as Filter.PriceFilter
                        productsList.filter { it.first.category == ProductCategory.CLOTHING && it.first.price.toLong() in filter.start .. filter.end }
                    }
                    FilterBy.STATUS -> {
                        val filter : Filter.StatusFilter = finalFilter as Filter.StatusFilter
                        productsList.filter { it.first.category == ProductCategory.CLOTHING && it.second.status == filter.status }
                    }
                    FilterBy.GENDER -> {
                        val filter : Filter.GenderFilter = finalFilter as Filter.GenderFilter
                        productsList.filter { it.first.category == ProductCategory.CLOTHING && (it.first as ProductSku.Clothing).gender.gender == filter.gender }

                    }
                    FilterBy.COLOUR -> {
                        val filter : Filter.ColourFilter = finalFilter as Filter.ColourFilter
                        productsList.filter { it.first.category == ProductCategory.CLOTHING && (it.first as ProductSku.Clothing).colour.colour == filter.colour }

                    }
                    else -> { emptyList() }
                }

            }
            ProductCategory.EARPHONE -> {
                when(filterOption) {
                    FilterBy.PRICE -> {
                        val filter : Filter.PriceFilter = finalFilter as Filter.PriceFilter
                        productsList.filter { it.first.category == ProductCategory.EARPHONE && it.first.price.toLong() in filter.start .. filter.end }
                    }
                    FilterBy.STATUS -> {
                        val filter : Filter.StatusFilter = finalFilter as Filter.StatusFilter
                        productsList.filter { it.first.category == ProductCategory.EARPHONE && it.second.status == filter.status }
                    }
                    FilterBy.BRAND -> {
                        val filter : Filter.BrandFilter = finalFilter as Filter.BrandFilter
                        productsList.filter { it.first.category == ProductCategory.EARPHONE && (it.first as ProductSku.Earphone).brand.brandName == filter.brandName}

                    }
                    else -> { emptyList() }
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
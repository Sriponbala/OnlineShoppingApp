package data

sealed class Filters {

    sealed class PriceFilters: Filters() {
        abstract val start: Long
        abstract val end: Long

        data class Below_100(override val start: Long = 0, override val end: Long = 99): PriceFilters()
        data class Between_100_And_500(override val start: Long = 100, override val end: Long = 499): PriceFilters()
        data class From_500_To_3000(override val start: Long = 500, override val end: Long = 3000): PriceFilters()
        data class Above_3000(override val start: Long = 3001, override val end: Long = 9_223_372_036_854_775_807): PriceFilters()
        data class Below_15000(override val start: Long = 0, override val end: Long = 14999): PriceFilters()
        data class From_15000_To_40000(override val start: Long = 15000, override val end: Long = 40000): PriceFilters()
        data class From_40000_100000(override val start: Long = 40000, override val end: Long = 100000): PriceFilters()
        data class Above_100000(override val start: Long = 100001, override val end: Long = 9_223_372_036_854_775_807): PriceFilters()
    }

    sealed class BrandFilters: Filters() {
        abstract val brandName: String

        data class Apple(override val brandName: String = "Apple"): BrandFilters()
        data class Boat(override val brandName: String = "boAt"): BrandFilters()
        data class Samsung(override val brandName: String = "Samsung"): BrandFilters()
        data class Zebronics(override val brandName: String = "ZEBRONICS"): BrandFilters()
        data class Generics(override val brandName: String = "Generics"): BrandFilters()
    }

    sealed class StatusFilters: Filters() {
        abstract val status: String

        data class InStock(override val status: String = "In Stock"): StatusFilters()
        data class OutOfStock(override val status: String = "Out Of Stock"): StatusFilters()
    }

    sealed class CategoryFilters: Filters() {
        abstract val category: String

        data class Book(override val category: String = "Book"): CategoryFilters()
        data class Mobile(override val category: String = "Mobile"): CategoryFilters()
        data class Clothing(override val category: String = "Clothing"): CategoryFilters()
        data class Earphone(override val category: String = "Earphone"): CategoryFilters()
    }

    sealed class EarPhoneTypeFilters: Filters() {
        abstract val type: String

        data class Wired(override val type: String = "Wired"): EarPhoneTypeFilters()
        data class TrueWireless(override val type: String = "TWS"): EarPhoneTypeFilters()
        data class Wireless(override val type: String = "Wireless"): EarPhoneTypeFilters()
    }

    sealed class BookTypeFilters: Filters() {
        abstract val type: String

        data class Fiction(override val type: String = "Fiction"): BookTypeFilters()
        data class NonFiction(override val type: String = "Non-Fiction"): BookTypeFilters()
    }

    sealed class StorageFilters: Filters() {
        abstract val storage: String

        data class Storage_128GB(override val storage: String = "128GB"): StorageFilters()
        data class Storage_64GB(override val storage: String = "64GB"): StorageFilters()

    }

    sealed class ColourFilters: Filters() {
        abstract val colour: String

        data class Blue(override val colour: String = "Blue"): ColourFilters()
        data class Red(override val colour: String = "Red"): ColourFilters()
        data class Black(override val colour: String = "Black"): ColourFilters()
    }

    sealed class GenderFilters: Filters() {
        abstract val gender: String

        data class Female(override val gender: String = " Female"): GenderFilters()
        data  class Male(override val gender: String = "Male"): GenderFilters()
        data class Others(override val gender: String = "Others"): GenderFilters()
    }
}


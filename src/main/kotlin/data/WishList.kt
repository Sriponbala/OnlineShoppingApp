package data

data class WishList(val wishListProducts: ArrayList<Product> = arrayListOf()) { // map - productId, product
    companion object {
        const val wishListName = "My WishList"
    }
}
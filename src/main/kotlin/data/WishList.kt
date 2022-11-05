package data

data class WishList(val wishListId: String, val wishListProducts: ArrayList<Product> = arrayListOf()) { // map - productId, product
    companion object {
        const val wishListName = "My WishList"
    }
}
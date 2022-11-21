package interfaces

import data.Product

interface WishListDao {

    fun retrieveWishListProducts(wishListId: String): ArrayList<Product>

    fun addAProductToWishList(wishListId: String, product: Product)

    fun deleteProductFromWishList(wishListId: String, product: Product)

    fun createAndGetWishListId(): String

    fun retrieveProductFromWishList(wishListId: String, productId: String): Product

}
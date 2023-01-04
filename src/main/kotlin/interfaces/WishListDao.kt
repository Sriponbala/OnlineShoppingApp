package interfaces

import data.ProductSku

interface WishListDao {

    fun retrieveWishListProducts(wishListId: String): ArrayList<ProductSku>

    fun addAProductToWishList(wishListId: String, skuId: String)

    fun deleteProductFromWishList(wishListId: String, skuId: String)

    fun createAndGetWishListId(userId: String): String

}
package utils

import data.ProductSku
import data.WishList
import data.WishListItem
import database.*
import interfaces.WishListDao

class WishListsData(private val userName: String = "root",
                    private val password: String = "tiger"): WishListDao {

    private val database: Database = Database.getConnection(this.userName, this.password)!!

    override fun retrieveWishListProducts(wishListId: String): ArrayList<ProductSku> {
        val productsList: ArrayList<ProductSku> = arrayListOf()
        for(wishList in database.usersWishList) {
            if(wishListId == wishList.wishListId) {
                for(wishListItem in database.wishListItems) {
                    for(productSku in database.productSkus) {
                        if(wishListItem.skuId == productSku.skuId) {
                            productsList.add(productSku)
                            break
                        }
                    }
                }
            }
        }
        return productsList
    }

    override fun addAProductToWishList(wishListId: String, skuId: String) {
        val wishListItem = WishListItem(wishListId, skuId)
        database.wishListItems.add(wishListItem)
    }

    override fun deleteProductFromWishList(wishListId: String, skuId: String) {
        val iter = database.wishListItems.iterator()
        for(it in iter) {
            if (it.wishListId == wishListId && it.skuId == skuId) {
                iter.remove()
            }
        }
    }

    override fun createAndGetWishListId(userId: String): String {
        val wishList = WishList(userId)
        val wishListId = wishList.wishListId
        database.usersWishList.add(wishList)
        return wishListId
    }

}
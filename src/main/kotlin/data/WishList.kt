package data

import utils.Helper

data class WishList(val userId: String) {

    val wishListId = Helper.generateWishListId()
    companion object {
        const val wishListName = "MY WISHLIST"
    }
}

package database

import data.WishList

object WishListTable {

    val usersWishList: MutableMap<String, WishList> = mutableMapOf() // wishListId, userWishlist

    private var wishListId = 1
    fun generateWishListId(): String {
        return "WL${wishListId++}"
    }

}



















package data

data class WishLists(var userMobile: String, val wishLists: MutableList<WishList> = mutableListOf())

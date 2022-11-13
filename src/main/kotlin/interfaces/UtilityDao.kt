package interfaces


interface UtilityDao {

    fun checkUniqueUser(mobile: String): Boolean

    fun validateLoginCredentials(mobile: String, password: String) : Boolean

    fun checkIfUserExists(userId: String): Boolean

    fun checkIfUserAccountInfoExists(userId: String): Boolean

    fun checkIfWishListExists(wishListId: String): Boolean

    fun checkIfProductIsInUserWishList(wishListId: String, productId: String): Boolean

    fun checkIfCategoryExistsInProductDB(category: String): Boolean

    fun checkIfProductExists(productId: String, category: String): Boolean

    fun checkIfOrdersHistoryExists(ordersHistoryId: String): Boolean

    fun checkIfCartExists(cartId: String): Boolean

    fun checkIfItemIsInCart(cartId: String, productId: String): Boolean

}
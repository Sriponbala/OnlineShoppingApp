package interfaces

interface UtilityDao {

    fun checkUniqueUser(mobile: String): Boolean

    fun validateLoginCredentials(mobile: String, password: String) : Boolean

    fun checkIfUserExists(userId: String): Boolean

    fun checkIfUserAccountInfoExists(userId: String): Boolean

    fun checkIfWishListExists(wishListId: String): Boolean

    fun checkIfProductIsInUserWishList(wishListId: String, skuId: String): Boolean

    fun checkIfProductExists(skuId: String): Boolean

    fun checkIfOrdersHistoryExists(ordersHistoryId: String): Boolean

    fun checkIfCartExists(cartId: String): Boolean

    fun checkIfItemIsInCart(cartId: String, skuId: String): Boolean

    fun checkIfAddressExists(addressId: String): Boolean

    fun checkIfCartIsEmpty(cartId: String): Boolean

}
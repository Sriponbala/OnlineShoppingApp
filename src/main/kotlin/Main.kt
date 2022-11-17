import backend.*
import interfaces.UtilityDao
import userInterface.*
import utils.Utility

fun main() {

    val utility: UtilityDao = Utility()

    val productActivities = ProductActivities(utility)
    val userAccountActivities = UserAccountActivities(utility)
    val cartActivities = CartActivities(utility)
    val wishListsActivities = WishListsActivities(utility)
    val ordersHistoryActivities = OrdersHistoryActivities(utility)
    val checkOutActivities = CheckOutActivities(cartActivities, productActivities, ordersHistoryActivities)

    val entryPage = EntryPage()
    val addressPage = AddressPage(userAccountActivities)
    val cartPage = CartPage(cartActivities)
    val checkOutPage = CheckOutPage(checkOutActivities)
    val homePage = HomePage(cartActivities, wishListsActivities)
    val ordersPage = OrdersPage(ordersHistoryActivities)
    val paymentPage = PaymentPage()
    val shopPage = ShopPage(productActivities)
    val signUpPage = SignUpPage()
    val signInPage = SignInPage()
    val userAccountPage = UserAccountPage(userAccountActivities)
    val wishListPage = WishListPage(wishListsActivities)

    entryPage.openEntryPage(signUpPage, signInPage, homePage, userAccountPage, shopPage, cartPage, wishListPage, checkOutPage, addressPage, ordersPage, paymentPage, userAccountActivities, wishListsActivities, cartActivities, ordersHistoryActivities, utility)

}










/*
/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=55478:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home/jre/lib/rt.jar:/Users/sri-pt6279/IdeaProjects/OnlineShoppingApp/build/classes/kotlin/main:/Users/sri-pt6279/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk8/1.7.10/d70d7d2c56371f7aa18f32e984e3e2e998fe9081/kotlin-stdlib-jdk8-1.7.10.jar:/Users/sri-pt6279/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-jdk7/1.7.10/1ef73fee66f45d52c67e2aca12fd945dbe0659bf/kotlin-stdlib-jdk7-1.7.10.jar:/Users/sri-pt6279/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/1.7.10/d2abf9e77736acc4450dc4a3f707fa2c10f5099d/kotlin-stdlib-1.7.10.jar:/Users/sri-pt6279/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-common/1.7.10/bac80c520d0a9e3f3673bc2658c6ed02ef45a76a/kotlin-stdlib-common-1.7.10.jar:/Users/sri-pt6279/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar MainKt
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 1
----------SignUp Page----------
Enter name:
sri
Enter mobile number:
1234567890
Enter email:
sri@gmail.com
Enter password: [Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~][It should contain at least 4 to 8 characters]
sri01
Enter confirm password:
sri01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: {}
OTP : 674414
Enter the OTP:
674414
{USER1=User(userId=USER1, userName=sri, userMobile=1234567890, userEmail=sri@gmail.com)}
users: kotlin.Unit
SignUp Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 2
----------SignIn Page----------
Enter mobile number:
1234567890
Enter password:
[Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~]
[It should contain at least 4 to 8 characters]
sri01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: login() : {USER1=User(userId=USER1, userName=sri, userMobile=1234567890, userEmail=sri@gmail.com)}
SignIn Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
No items found in cart!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
3
---------------Your Profile--------------
Name   : sri
Mobile : 1234567890
Email  : sri@gmail.com
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
1
-------------------My WishList----------------------
        No items found
Confirm:
1. CONTINUE
2. GO_BACK
1
-------------------My WishList----------------------
        No items found
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
2
-----------------Orders History---------------------
         Empty orders history
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
3
-------------Edit User Details------------
Select the field to edit:
1. Name
2. Email
3. Addresses
4. Back
1
Enter name:
Bala
-------------Edit User Details------------
Select the field to edit:
1. Name
2. Email
3. Addresses
4. Back
3
        Empty addresses list
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
2
        Empty addresses list
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
1
Fill address fields:
Enter door number:
1
Enter flat name:
1
Enter street:
1
Enter area:
1
Enter city:
1
Enter state:
1
Enter pincode:
111111
Confirm:
1. CONTINUE
2. GO_BACK
2
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
1
Fill address fields:
Enter door number:
1
Enter flat name:
1
Enter street:
1
Enter area:
1
Enter city:
1
Enter state:
1
Enter pincode:
111111
Confirm:
1. CONTINUE
2. GO_BACK
1
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
2
-----------------Your Addresses------------------
1. 1, 1, 1, 1, 1, 1, 111111
Select an address:
1
Selected address: 1, 1, 1, 1, 1, 1, 111111
-------------ADDRESS MANAGEMENT OPTIONS-------------
1. EDIT
2. DELETE
3. BACK
1
-------------ADDRESS FIELDS-------------
1. DOORNUMBER
2. FLATNAME
3. STREET
4. AREA
5. CITY
6. STATE
7. PINCODE
8. BACK
1
Enter door number:
b1s3
-------------ADDRESS FIELDS-------------
1. DOORNUMBER
2. FLATNAME
3. STREET
4. AREA
5. CITY
6. STATE
7. PINCODE
8. BACK
8
-----------------Your Addresses------------------
1. b1s3, 1, 1, 1, 1, 1, 111111
Select an address:
1
Selected address: b1s3, 1, 1, 1, 1, 1, 111111
-------------ADDRESS MANAGEMENT OPTIONS-------------
1. EDIT
2. DELETE
3. BACK
2
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
2
        Empty addresses list
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
3
-------------Edit User Details------------
Select the field to edit:
1. Name
2. Email
3. Addresses
4. Back
4
user : {name=Bala, mobile=1234567890, email=sri@gmail.com}
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
4
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
1
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a category:
1
Confirm:
1. CONTINUE
2. GO_BACK
1
------------------books--------------------
1. Book1 - 200.0
2. Book2 - 110.0
3. Book3 - 200.0
4. Book4 - 250.0
5. Book5 - 250.0
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a product:
1
Confirm:
1. CONTINUE
2. GO_BACK
1
Product name       : Book1
Product price      : 200.0
Book type          : fiction
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Product added to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Can't add to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
2
Product added to wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
2
Product already added to wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
3
Product removed from wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
3
Product not yet added to wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
2
Product added to wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
4
1. Product Name  : Book1
Product price : 200.0
Quantity      : 1
Total Price   : 200.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
-------------BUY NOW PAGE-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
1
1. Product Name  : Book1
Product price : 200.0
Quantity      : 1
Total Price   : 200.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
-------------BUY NOW PAGE-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
3
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
5
------------------books--------------------
1. Book1 - 200.0
2. Book2 - 110.0
3. Book3 - 200.0
4. Book4 - 250.0
5. Book5 - 250.0
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a product:
5
Confirm:
1. CONTINUE
2. GO_BACK
1
Product name       : Book5
Product price      : 250.0
Book type          : nonfiction
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Product added to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
5
------------------books--------------------
1. Book1 - 200.0
2. Book2 - 110.0
3. Book3 - 200.0
4. Book4 - 250.0
5. Book5 - 250.0
Confirm:
1. CONTINUE
2. GO_BACK
2
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
1. Product Name  : Book1
Product price : 200.0
Quantity      : 1
Total Price   : 200.0
Status        : IN_STOCK
2. Product Name  : Book5
Product price : 250.0
Quantity      : 1
Total Price   : 250.0
Status        : IN_STOCK
-------------CART DASHBOARD-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
3
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
1
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a category:
2
Confirm:
1. CONTINUE
2. GO_BACK
1
------------------mobiles--------------------
1. iPhone 14 128GB - 79900.0
2. Samsung Galaxy M33 5G - 15499.0
3. Samsung Galaxy S20 FE 5G - 29900.0
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a product:
1
Confirm:
1. CONTINUE
2. GO_BACK
1
Product name       : iPhone 14 128GB
Product price      : 79900.0
Brand              : Apple
Storage            : 128 GB
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Product added to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
5
------------------mobiles--------------------
1. iPhone 14 128GB - 79900.0
2. Samsung Galaxy M33 5G - 15499.0
3. Samsung Galaxy S20 FE 5G - 29900.0
Confirm:
1. CONTINUE
2. GO_BACK
2
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a category:
1
Confirm:
1. CONTINUE
2. GO_BACK
1
------------------books--------------------
1. Book1 - 200.0
2. Book2 - 110.0
3. Book3 - 200.0
4. Book4 - 250.0
5. Book5 - 250.0
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a product:
1
Confirm:
1. CONTINUE
2. GO_BACK
1
Product name       : Book1
Product price      : 200.0
Book type          : fiction
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Can't add to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
4
1. Product Name  : Book1
Product price : 200.0
Quantity      : 1
Total Price   : 200.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
-------------BUY NOW PAGE-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
2
Select an address:
        Empty addresses list
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
1
Fill address fields:
Enter door number:
1
Enter flat name:
1
Enter street:
11
Enter area:
1
Enter city:
1
Enter state:
1
Enter pincode:
111111
Confirm:
1. CONTINUE
2. GO_BACK
1
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
3
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
4
1. Product Name  : Book1
Product price : 200.0
Quantity      : 1
Total Price   : 200.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
-------------BUY NOW PAGE-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
2
Select an address:
-----------------Your Addresses------------------
1. 1, 1, 11, 1, 1, 1, 111111
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
2
-----------------Your Addresses------------------
1. 1, 1, 11, 1, 1, 1, 111111
Select an address:
1
Selected address: 1, 1, 11, 1, 1, 1, 111111
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
3
Confirm:
1. CONTINUE
2. GO_BACK
1
Select mode of payment:
Confirm:
1. CONTINUE
2. GO_BACK
1
Do you want to place order?
Confirm:
1. CONTINUE
2. GO_BACK
1
Orders added to orders  history!
Ordered placed! Item removed from cart
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
5
------------------books--------------------
1. Book1 - 200.0
2. Book2 - 110.0
3. Book3 - 200.0
4. Book4 - 250.0
5. Book5 - 250.0
Confirm:
1. CONTINUE
2. GO_BACK
2
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
1. Product Name  : Book5
Product price : 250.0
Quantity      : 1
Total Price   : 250.0
Status        : IN_STOCK
2. Product Name  : iPhone 14 128GB
Product price : 79900.0
Quantity      : 1
Total Price   : 79900.0
Status        : IN_STOCK
-------------CART DASHBOARD-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
1
Select an item:
1
Selected address: Product Name  : Book5
Product price : 250.0
Quantity      : 1
Total Price   : 250.0
Status        : IN_STOCK
-------------ACTIVITIES ON SELECTED PRODUCT-------------
1. CHANGE_QUANTITY
2. REMOVE
3. GO_BACK
3
1. Product Name  : Book5
Product price : 250.0
Quantity      : 1
Total Price   : 250.0
Status        : IN_STOCK
2. Product Name  : iPhone 14 128GB
Product price : 79900.0
Quantity      : 1
Total Price   : 79900.0
Status        : IN_STOCK
-------------CART DASHBOARD-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
2
1. Product Name  : Book5
Product price : 250.0
Quantity      : 1
Total Price   : 250.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
2. Product Name  : iPhone 14 128GB
Product price : 79900.0
Quantity      : 1
Total Price   : 79900.0
Status        : IN_STOCK
Ordered Date : 06-11-2022
Delivery Date: 16-11-2022
-------------BUY NOW PAGE-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
2
Select an address:
-----------------Your Addresses------------------
1. 1, 1, 11, 1, 1, 1, 111111
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
2
-----------------Your Addresses------------------
1. 1, 1, 11, 1, 1, 1, 111111
Select an address:
1
Selected address: 1, 1, 11, 1, 1, 1, 111111
-------------ADDRESS PAGE DASHBOARD-------------
1. ADD_NEW_ADDRESS
2. SAVED_ADDRESS
3. GO_BACK
3
Confirm:
1. CONTINUE
2. GO_BACK
1
Select mode of payment:
Confirm:
1. CONTINUE
2. GO_BACK
1
Do you want to place order?
Confirm:
1. CONTINUE
2. GO_BACK
1
Orders added to orders  history!
Orders placed! Items removed from cart
No items found in cart!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
No items found in cart!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
3
---------------Your Profile--------------
Name   : Bala
Mobile : 1234567890
Email  : sri@gmail.com
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
1
-------------------My WishList----------------------
1. Book1 - 200.0
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
2
-----------------Orders History---------------------
1. Book1 - 200.0 - 200.0 - 1 - 06-11-2022 - 16-11-2022
2. Book5 - 250.0 - 250.0 - 1 - 06-11-2022 - 16-11-2022
3. iPhone 14 128GB - 79900.0 - 79900.0 - 1 - 06-11-2022 - 16-11-2022
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
4
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 1
----------SignUp Page----------
Enter name:
d
Enter mobile number:
0987654321
Enter email:
sri@gmail.com
Enter password: [Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~][It should contain at least 4 to 8 characters]
sri01
Enter confirm password:
sri01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: {USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com)}
OTP : 393646
Enter the OTP:
393646
{USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com), USER2=User(userId=USER2, userName=d, userMobile=0987654321, userEmail=sri@gmail.com)}
users: kotlin.Unit
SignUp Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
1
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a category:
2
Confirm:
1. CONTINUE
2. GO_BACK
1
------------------mobiles--------------------
1. iPhone 14 128GB - 79900.0
2. Samsung Galaxy M33 5G - 15499.0
3. Samsung Galaxy S20 FE 5G - 29900.0
Confirm:
1. CONTINUE
2. GO_BACK
1
Select a product:
2
Confirm:
1. CONTINUE
2. GO_BACK
1
Product name       : Samsung Galaxy M33 5G
Product price      : 15499.0
Brand              : Samsung
Storage            : 128 GB
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Product added to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
1
Can't add to cart!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
2
Product added to wishlist!
-------------PRODUCT DASHBOARD-------------
1. ADD_TO_CART
2. ADD_TO_WISHLIST
3. REMOVE_FROM_WISHLIST
4. BUY_NOW
5. GO_BACK
5
------------------mobiles--------------------
1. iPhone 14 128GB - 79900.0
2. Samsung Galaxy M33 5G - 15499.0
3. Samsung Galaxy S20 FE 5G - 29900.0
Confirm:
1. CONTINUE
2. GO_BACK
2
---------------Categories----------------
1. Book
2. Mobile
3. Clothing
4. Earphone
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
1. Product Name  : Samsung Galaxy M33 5G
Product price : 15499.0
Quantity      : 1
Total Price   : 15499.0
Status        : IN_STOCK
-------------CART DASHBOARD-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
3
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 1
----------SignUp Page----------
Enter name:
l
Enter mobile number:
9080440195
Enter email:
bala@01
Enter email:
bala@gmail.com
Enter password: [Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~][It should contain at least 4 to 8 characters]
bala01
Enter confirm password:
bala01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: {USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com), USER2=User(userId=USER2, userName=d, userMobile=0987654321, userEmail=sri@gmail.com)}
OTP : 888459
Enter the OTP:
888459
{USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com), USER2=User(userId=USER2, userName=d, userMobile=0987654321, userEmail=sri@gmail.com), USER3=User(userId=USER3, userName=l, userMobile=9080440195, userEmail=bala@gmail.com)}
users: kotlin.Unit
SignUp Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 2
----------SignIn Page----------
Enter mobile number:
0987654321
Enter password:
[Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~]
[It should contain at least 4 to 8 characters]
sri01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: login() : {USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com), USER2=User(userId=USER2, userName=d, userMobile=0987654321, userEmail=sri@gmail.com), USER3=User(userId=USER3, userName=l, userMobile=9080440195, userEmail=bala@gmail.com)}
SignIn Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
1. Product Name  : Samsung Galaxy M33 5G
Product price : 15499.0
Quantity      : 1
Total Price   : 15499.0
Status        : IN_STOCK
-------------CART DASHBOARD-------------
1. SELECT_A_PRODUCT
2. PROCEED_TO_BUY
3. GO_BACK
3
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 2
----------SignIn Page----------
Enter mobile number:
1234567890
Enter password:
[Password can contain any of the following : a-zA-Z0-9!#@$%^&*_+`~]
[It should contain at least 4 to 8 characters]
sri01
Confirm:
1. CONTINUE
2. GO_BACK
1
Users: login() : {USER1=User(userId=USER1, userName=Bala, userMobile=1234567890, userEmail=sri@gmail.com), USER2=User(userId=USER2, userName=d, userMobile=0987654321, userEmail=sri@gmail.com), USER3=User(userId=USER3, userName=l, userMobile=9080440195, userEmail=bala@gmail.com)}
SignIn Successful!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
2
No items found in cart!
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
3
---------------Your Profile--------------
Name   : Bala
Mobile : 1234567890
Email  : sri@gmail.com
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
1
-------------------My WishList----------------------
1. Book1 - 200.0
Confirm:
1. CONTINUE
2. GO_BACK
2
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
2
-----------------Orders History---------------------
1. Book1 - 200.0 - 200.0 - 1 - 06-11-2022 - 16-11-2022
2. Book5 - 250.0 - 250.0 - 1 - 06-11-2022 - 16-11-2022
3. iPhone 14 128GB - 79900.0 - 79900.0 - 1 - 06-11-2022 - 16-11-2022
------------Your Account-------------
1. VIEW_WISHLIST
2. VIEW_ORDERS_HISTORY
3. EDIT_ACCOUNT
4. GO_BACK
4
------------Home Page-------------
1. VIEW_PRODUCTS
2. VIEW_CART
3. YOUR_ACCOUNT
4. SIGN_OUT
4
Signed out...
------Online Shopping Application------
1.SIGN_UP
2.SIGN_IN
3.EXIT
Enter your choice: 3
Thank You! Visit again!

 */
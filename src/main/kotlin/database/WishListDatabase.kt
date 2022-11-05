package database

import data.Product
import data.WishList
import utils.ProductsData

//class WishListDatabase private constructor() {
//
//    private
//    companion object {
//        private val INSTANCE by lazy { WishListDatabase() }
//        private const val USERNAME = "root"
//        private const val PASSWORD = "tiger"
//        fun getInstance(userName: String, password: String): WishListDatabase? {
//
//            return if (userName == USERNAME && password == PASSWORD) {
//                INSTANCE
//            } else {
//                null
//            }
//        }
//    }

object WishListDatabase {

    val usersWishList: MutableMap<String, WishList> = mutableMapOf() // userId, userWishlist
//
//    fun addProductToWishListOfUser(userId: String, product: Product) {
//        if (usersWishList.containsKey(userId)) {
//            usersWishList[userId]?.wishListProducts!!.add(product)
//        }
//    }
//
//    fun getWishList(userId: String): WishList? {
//        return if (usersWishList.containsKey(userId)) {
//            usersWishList[userId]
//        } else {
//            null
//        }
//    }
//
//    fun addWishList(userId: String, wishList: WishList) {
//        usersWishList[userId] = wishList
//    }
//
//    fun deleteProductFromWishList(userId: String, productId: String) {
//        if(usersWishList.containsKey(userId)) {
//            for (product in usersWishList[userId]!!.wishListProducts) {
//                if(productId == product.productId) {
//                    usersWishList[userId]?.wishListProducts?.remove(product)
//                }
//            }
//        }
//    }

}


//    fun getWishListsOfUser(userId: String): ArrayList<WishList>? {
//        val wishLists: ArrayList<WishList>? = if(usersWishLists.containsKey(userId)) {
//            usersWishLists[userId]
//        } else {
//            null
//        }
//        return wishLists
//    }
//
//    fun addWishListToWishListsOfUser(userId: String, wishList: WishList) {
//        if(usersWishLists.containsKey(userId)) {
//            usersWishLists[userId]?.add(wishList)
//        }
//    }
//
//    fun deleteAWishListOfUser(userId: String, wishListName: String) {
//        if(usersWishLists.containsKey(userId)) {
//            usersWishLists[userId]?.forEach {
//                if(it.wishListName == wishListName) {
//                    usersWishLists[userId]?.remove(it)
//                }
//            }
//        }
//    }




//data class W(val name: String)
//
//class A{
//    val a = arrayListOf<W>()
//}
//
//fun main() {
//    val classA = A()
//    val a = classA.a
//    a.add(W("Sri"))
//    a.add(W("Pon"))
//    a.add(W("Bala"))
//    a.forEachIndexed{ i,w ->
//        println("${i + 1}. $w")
//    }
//
//    a.removeAt(1)
//
//    a.forEachIndexed{ i,w ->
//        println("${i + 1}. $w")
//    }
//
//}

//class A{
//    private val a = D()
//    private var i = 0
//    private val listu = a.getAlist()
//    fun add(){
//        listu.add(i++)
//    }
//
//    fun returnListu() = listu
//
//}
//class Be private constructor(){
//    companion object{
//        private val INSTANCE by lazy { Be() }
//
//        fun getInstance() = INSTANCE
//    }
//    private val alist = arrayListOf<Int>()
//
//    fun prinList() {
//        println("alist - $alist")
//    }
//
//    fun getAlist(): ArrayList<Int> {
//        val l = alist
//        return l
//    }
//}
//
//class C{
//    val li = A().returnListu()
//
//    fun add(){
//        li.add(24)
//    }
//}
//
//class D {
//    private val alist = arrayListOf<Int>()
//
//    fun prinList() {
//        println("alist - $alist")
//    }
//
//    fun getAlist(): ArrayList<Int> {
//        val l = alist
//        return l
//    }
//}
//
//fun main(){
//    val classA = A()
//    val be = Be.getInstance()
//    val c = C()
//    val d = D()
//
////    println("Be - ${be.getAlist()}")
////    println("A - ${classA.returnListu()}")
////
////    classA.add()
////    println("Be - ${be.getAlist()}")
////    println("A - ${classA.returnListu()}")
////
////    c.add()
////    println("Be - ${be.getAlist()}")
////    println("A - ${classA.returnListu()}")
////    println("C - ${C().li}")
//
//    println("D - ${d.getAlist()}")
//    println("A - ${classA.returnListu()}")
//
//    classA.add()
//    println("D - ${d.getAlist()}")
//    println("A - ${classA.returnListu()}")
//
//    c.add()
//    println("D - ${d.getAlist()}")
//    println("A - ${classA.returnListu()}")
//    println("C - ${c.li}")
//}

//data class Person(val id: Int, val name: String)
//
//class Database(){
//    val data = arrayListOf<Person>(Person(1,"S"), Person(2,"D"), Person(3,"R"))
//
//    val numbers = arrayListOf<Int>()
//
//    fun addNumbers(n: Int) {
//        numbers.add(n)
//        println(numbers)
//    }
//    fun add(id: Int, name: String) {
//        data.add(Person(id, name))
//    }
//
//    fun delete(id: Int) {
//        for(person in data){
//            if(person.id == id){
//                data.remove(person)
//                break
//            }
//        }
//    }
//
//    fun printData(){
//        println(data)
//    }
//}
//class AR() {
//    val database = Database()
//    fun create(id: Int, name: String) {
//        database.add(id, name)
//       // database.addNumbers(289)
//    }
//
//    fun delete(id: Int) {
//        database.delete(id)
//    }
//}
//
//class B() {
//    private val a = AR()
//    fun createPerson() {
//        //this.a.create(1,"Sri")
//        a.database.printData()
//        //this.a.create(2, "sree")
//        a.delete(2)
//    }
//}
//
//class C() {
//    private val a = AR()
//    init{
//
//       // this.a.create(10, "Bala")
//        a.database.printData()
//       // this.a.create(12, "Sara")
//        this.a.delete(1)
//    }
//}
//
//fun main() {
//    //val database = Database()
//    val a = AR()
//   // database.addNumbers(1)
//    a.database.printData()
//    val b = B()
//    b.createPerson()
//    val c = C()
//    a.database.printData()
//
//    //println("data - ${database.data}")
//    //database.printData()
//}
//class Base(){
//    var data = 0
//}
//class X() {
//    val base = Base()
//    fun returnBaseData() = base.data
//}
//class Y(){
//    init{ X().base.data = 9 }
//}
//class Z() {
//    init{ X().base.data = 90 }
//}
//class W(val x: X){
//    init {
//        x.base.data = 100
//    }
//}
//
//fun main(){
//    val x = X()
//    x.returnBaseData().also { println(it) }
//    Y()
//    x.returnBaseData().also { println(it) }
//    Z()
//    x.returnBaseData().also { println(it) }
//    W(x)
//    x.returnBaseData().also { println(it) }
//}


//
//val map = mutableMapOf<Int,String?>(1 to "", 2 to null)
//fun update(a: Int): String? {
//    return if(map.containsKey(a)) {
//        map[a]
//    }else{
//        null
//    }
//
//}
//fun main() {
//
//    val a = readLine()!!.toInt()
//    update(a).also{
//        print(it)
//    }
//}
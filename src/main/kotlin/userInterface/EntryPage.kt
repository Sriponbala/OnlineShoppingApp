package userInterface

import enums.Entry
import interfaces.DashboardServices
import utils.Helper

class EntryPage: DashboardServices {

    fun openEntryPage() {
        println("------Online Shopping Application------")
        val entry = Entry.values()
        while(true) {
            super.showDashboard("Entry Page", entry)
            when(super.getUserChoice(entry)) {
                Entry.VIEW_APP -> {
                    HomePage().openHomePage()
                }
                Entry.SIGN_UP -> {
                    SignUpPage().signUp()
                }
                Entry.SIGN_IN -> {
                    SignInPage().signIn()
                }
                Entry.EXIT -> {
                    println("Thank You! Visit again!")
                    break
                }
            }
        }
    }













//    private fun <E: Enum<E>> showDashboard(title: String, enumArray: Array<E>) {
//        println("-------------${title.uppercase()}-------------")
//        for(element in enumArray) {
//            println("${element.ordinal+1}. $element")
//        }
//    }
//
//    private fun <E: Enum<E>> getUserChoice(enumArray: Array<E>): E {
//        while (true) {
//            try {
//                println("Enter your choice: ")
//                val option = readLine()!!
//                val dashBoardOption = option.toInt()
//                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
//                    return enumArray[dashBoardOption-1]
//                } else {
//                    println("Enter valid option!")
//                }
//            } catch (exception: Exception) {
//                println("Class CartPage: getUserChoice(): Exception: $exception")
//            }
//        }
//    }

//    override fun showDashboard() {
//
//        while(true) {
//            println("------Online Shopping Application------")
//            for(entry in Entry.values()) {
//                println("${entry.ordinal + 1}.$entry")
//            }
//            print("Enter your choice: ")
//            try {
//                val option = readLine()!! // NumberFormatException
//                val entryOption = option.toInt()
//                if(Helper.checkValidRecord(entryOption, Entry.values().size)) {
//                    val entry: Entry = Entry.values()[entryOption-1]  // ArrayIndexOutOfBoundsException
//                    if(!doDashboardActivities(entry)) {
//                        break
//                    }
//                } else {
//                    println("\nEnter valid option!")
//                }
//            } catch (exception: Exception) {
//                println("Class EntryPage: showEntryPage(): Exception: $exception")
//            }
//        }
//    }
//
//    override fun <E : Enum<E>> doDashboardActivities(enumConstant: Enum<E>): Boolean {
//
//        when(enumConstant) {
//            Entry.SIGN_UP -> {
//                SignUpPage().signUp()
//                return true
//            }
//            Entry.SIGN_IN -> {
//                SignInPage().signIn()
//                return true
//            }
//            Entry.EXIT -> {
//                println("Thank You! Visit again!")
//                return false
//            }
//            else -> {
//                println("Invalid option!") // important to add else i.e. if enum changes then will throw error
//                return true
//            }
//        }
//    }
}
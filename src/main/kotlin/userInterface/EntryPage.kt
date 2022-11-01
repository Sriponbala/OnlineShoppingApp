package userInterface

import enums.Entry
import interfaces.DashboardServices
import utils.Helper

class EntryPage: DashboardServices {

    override fun showDashboard() {

        while(true) {
            println("------Online Shopping Application------")
            for(entry in Entry.values()) {
                println("${entry.ordinal + 1}.$entry")
            }
            print("Enter your choice: ")
            try {
                val option = readLine()!! // NumberFormatException
                val entryOption = option.toInt()
                if(Helper.checkValidRecord(entryOption, Entry.values().size)) {
                    val entry: Entry = Entry.values()[entryOption-1]  // ArrayIndexOutOfBoundsException
                    if(!doDashboardActivities(entry)) {
                        break
                    }
                } else {
                    println("\nEnter valid option!")
                }
            } catch (exception: Exception) {
                println("Class EntryPage: showEntryPage(): Exception: $exception")
            }
        }
    }

    override fun <E : Enum<E>> doDashboardActivities(enumConstant: Enum<E>): Boolean {

        when(enumConstant) {
            Entry.SIGN_UP -> {
                SignUpPage().signUp()
                return true
            }
            Entry.SIGN_IN -> {
                SignInPage().signIn()
                return true
            }
            Entry.EXIT -> {
                println("Thank You! Visit again!")
                return false
            }
            else -> {
                println("Invalid option!") // important to add else i.e. if enum changes then will throw error
                return true
            }
        }
    }
}
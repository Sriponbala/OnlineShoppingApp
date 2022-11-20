package interfaces

import utils.Helper

interface DashboardServices {

    fun <E: Enum<E>> showDashboard(title: String, enumArray: Array<E>) {

        println("-------------${title.uppercase()}-------------")
        for(element in enumArray) {
            println("${element.ordinal+1}. $element")
        }
    }

    fun <E: Enum<E>> getUserChoice(enumArray: Array<E>): E {

        while (true) {
            try {
                println("Enter your choice: ")
                val option = readLine()!!
                val dashBoardOption = option.toInt()
                if(Helper.checkValidRecord(dashBoardOption, enumArray.size)) {
                    return enumArray[dashBoardOption-1]
                } else {
                    println("Enter valid option!")
                }
            } catch (exception: Exception) {
                println("DashboardServices: getUserChoice(): Exception: $exception")
            }
        }
    }

}
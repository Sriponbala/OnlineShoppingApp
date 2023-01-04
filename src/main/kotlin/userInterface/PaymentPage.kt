package userInterface

import enums.Payment
import interfaces.DashboardServices
import utils.Helper

class PaymentPage: DashboardServices {

    private lateinit var modeOfPayment: Payment
    private fun selectModeOfPayment() {
        val payment = Payment.values()
        while(true) {
            super.showDashboard("PAYMENT OPTIONS", payment)
            modeOfPayment = super.getUserChoice(payment)
            if(Helper.confirm()) {
                break
            }
        }
    }

    fun getPaymentMethod(): Payment {
        selectModeOfPayment()
        return modeOfPayment
    }

    fun pay(totalBill: Float) {
       when(modeOfPayment) {
           Payment.CARD -> {
               println("Rs.$totalBill paid via card...")
           }
           Payment.UPI -> {
               println("Rs.$totalBill paid via upi...")
           }
           Payment.PAY_ON_DELIVERY -> {
               println("Rs.$totalBill will be paid on delivery..")
           }
       }
    }

}
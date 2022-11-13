package userInterface

import enums.Payment
import interfaces.DashboardServices
import utils.Helper

class PaymentPage: DashboardServices {

    private lateinit var modeOfPayment: Payment
    fun selectModeOfPayment() {
        val payment = Payment.values()
        while(true) {
            super.showDashboard("Payment Options", payment)
            modeOfPayment = super.getUserChoice(payment)
            if(Helper.confirm()) {
                break
            }
        }
    }

    fun pay(totalBill: Float) {
       when(modeOfPayment) {
           Payment.CARD -> {
               println("   Rs.$totalBill paid via card...")
           }
           Payment.UPI -> {
               println("   Rs.$totalBill paid via upi...")
           }
           Payment.PAY_ON_DELIVERY -> {
               println("   Rs.$totalBill will be paid on delivery..")
           }
       }
    }

}
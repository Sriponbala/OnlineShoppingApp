package interfaces

interface DashboardServices {
    fun showDashboard()
    fun <E: Enum<E>> doDashboardActivities(enum: Enum<E>): Boolean
}
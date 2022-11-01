package interfaces

interface DashboardServices {
    fun showDashboard()
    fun <E: Enum<E>> doDashboardActivities(enumConstant: Enum<E>): Boolean
}
package co.livil.workapi.model

interface ITimeslot {
    fun getTimeRangeText(): String
    fun getLabelText(): String
    fun getSortValue(): String
}
package co.livil.workapi.model

interface IMatchable {
    fun matchableContactStrings(): List<String>
    fun matchableKeywordStrings(): List<String>
    fun flagUrgent(isUrgent: Boolean)
}

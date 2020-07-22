package co.livil.workapi.view

interface ListableResourceComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}
package co.livil.workapi.model

import co.livil.dailybriefing.backend.model.Rule

interface IMatchable {
    fun matchesAccount(account: String): Boolean
    fun matchesContact(contact: String): Boolean
    fun matchesKeyword(keyword: String): Boolean
    fun matches(rule: Rule): Boolean {
       return when (rule.type) {
           "account" -> matchesAccount(rule.value)
           "contact" -> matchesContact(rule.value)
           "keyword" -> matchesKeyword(rule.value)
           else -> false
       }
    }
}

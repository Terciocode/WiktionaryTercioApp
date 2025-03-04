package org.terciolab.wiktionaryapp

enum class Language(val code: String, val displayName: String, val prefix: String) {
    ENGLISH("en", "English", "dictionary"),
    SPANISH("es", "Spanish", "eswiktionary")
}

fun getLanguageByCode(code: String) : Language {
    return Language.entries.find { l -> l.code == code } ?: Language.ENGLISH
}
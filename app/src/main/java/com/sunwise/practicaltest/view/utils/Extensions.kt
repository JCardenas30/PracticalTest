package com.sunwise.practicaltest.view.utils

import java.util.regex.Pattern

fun String.validWithRegex(regex: String): Boolean{
    if(isNullOrEmpty()) return false
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}
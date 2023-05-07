package com.xter.util

fun CharSequence?.containsLower(): Boolean {
    val pattern = "[a-z]+"
    val regex = Regex(pattern)
    return this != null && regex.containsMatchIn(this)
}

fun main(args: Array<String>) {
    val src1 = "213"
    val src2 = "213hf"
    println(src1.containsLower())
    println(src2.containsLower())
}
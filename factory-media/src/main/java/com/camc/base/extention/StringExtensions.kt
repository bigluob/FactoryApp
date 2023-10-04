package com.camc.base.extention

fun String.capitalize() = replaceFirstChar { it.uppercase() }
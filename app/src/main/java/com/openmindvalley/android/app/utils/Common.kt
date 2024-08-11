package com.openmindvalley.android.app.utils

fun CharSequence?.isNotNullOrEmpty(): Boolean  = !this.isNullOrEmpty()

fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()
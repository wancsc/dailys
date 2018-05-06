package com.frozen.dailys.components.models

open class Response<T> {
    var code: Int = 0
    var msg: String? = ""
    var data: T? = null

    fun isOk(): Boolean {
        if (code == 0) return true
        return false
    }
}
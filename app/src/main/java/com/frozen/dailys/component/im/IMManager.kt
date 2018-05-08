package com.frozen.dailys.component.im

import okhttp3.WebSocket

/**
 * Created by csc on 2018/5/2.
 */
class IMManager {
    private var webSocket: WebSocket? = null

    companion object {
        fun newInstance() = Inner.INSTANCE
    }

    private object Inner {
        val INSTANCE = IMManager()
    }

    fun start(webSocket: WebSocket?) {
        this.webSocket = webSocket
    }


    fun send(string: String) {
        webSocket?.send(string)
    }
}
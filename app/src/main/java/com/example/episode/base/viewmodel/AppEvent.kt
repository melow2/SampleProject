package com.example.episode.base.viewmodel

open class AppEvent<out T>(private val content: T) {
    var hasBennHandled = false
        private set

    fun getContentIfNotHandled():T?{
        return if(hasBennHandled) {
            null
        }else{
            hasBennHandled = true
            content
        }
    }
    fun peekContent():T = content
}
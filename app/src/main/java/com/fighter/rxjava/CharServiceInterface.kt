package com.fighter.rxjava

interface CharServiceInterface {
    fun onEventReceived(event: Char)
    fun onError(e:Throwable)
    fun onComplete()
}
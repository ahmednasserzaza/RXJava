package com.fighter.rxjava

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class CharService(private val listener: CharServiceInterface) : Service() {
    private val eventSubject = PublishSubject
        .interval(1, TimeUnit.SECONDS)
        .take(26)
        .map { 'A' + it.toInt() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    fun subscribe() {
        eventSubject.subscribe(::onNext, ::onError, ::onComplete)
    }

    private fun onNext(event: Char) {
        listener.onEventReceived(event)
    }

    private fun onError(e: Throwable) {
        Log.e("onError", e.message.toString())
        listener.onError(e)
    }

    private fun onComplete() {
        Log.e("onComplete", "Completed")
        listener.onComplete()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
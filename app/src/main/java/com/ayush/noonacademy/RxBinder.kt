package com.ayush.noonacademy

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxBinder {
    val iOScheduler: Scheduler
        get() = Schedulers.io()

    val androidScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
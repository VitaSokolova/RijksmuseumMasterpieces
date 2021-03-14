package com.rijksmuseum.masterpieces.infrastructure

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Android platform [SchedulersProvider] implementation
 */
class SchedulersProviderImpl : SchedulersProvider {

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun worker(): Scheduler = Schedulers.io()
}

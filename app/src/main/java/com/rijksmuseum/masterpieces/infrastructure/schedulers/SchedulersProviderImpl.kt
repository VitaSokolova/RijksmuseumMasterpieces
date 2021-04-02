package com.rijksmuseum.masterpieces.infrastructure.schedulers

import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Android platform [SchedulersProvider] implementation
 */
class SchedulersProviderImpl @Inject constructor(): SchedulersProvider {

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun worker(): Scheduler = Schedulers.io()
}

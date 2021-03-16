package com.rijksmuseum.masterpieces.common

import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Implementation of SchedulersProvider for tests
 */
class TestsSchedulersProvider :
    SchedulersProvider {

    override fun main(): Scheduler = Schedulers.trampoline()

    override fun worker(): Scheduler = Schedulers.trampoline()
}
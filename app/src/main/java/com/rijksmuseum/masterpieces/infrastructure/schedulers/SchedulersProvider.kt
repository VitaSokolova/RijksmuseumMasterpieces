package com.rijksmuseum.masterpieces.infrastructure.schedulers

import io.reactivex.rxjava3.core.Scheduler

/**
 * Abstraction above platform schedulers
 */
interface SchedulersProvider {
    
    fun main(): Scheduler

    fun worker(): Scheduler
}
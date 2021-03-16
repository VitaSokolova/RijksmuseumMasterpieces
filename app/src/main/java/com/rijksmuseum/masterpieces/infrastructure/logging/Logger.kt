package com.rijksmuseum.masterpieces.infrastructure.logging

/**
 * Abstraction above platform logger
 */
interface Logger {

    fun v(msg: String)

    fun e(msg: String)
}
package com.rijksmuseum.masterpieces.common

import com.rijksmuseum.masterpieces.infrastructure.logging.Logger

/**
 * Stub implementation of Logger for tests
 */
class TestLogger : Logger {

    override fun v(msg: String) {}

    override fun e(msg: String) {}
}
package com.korrit.kotlin.ktor.controllers.exceptions

import io.ktor.server.plugins.*

/**
 * General exception related to input.
 */
open class InputException(message: String, cause: Throwable? = null) : BadRequestException(message, cause)

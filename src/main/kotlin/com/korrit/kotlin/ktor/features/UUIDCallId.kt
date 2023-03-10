package com.korrit.kotlin.ktor.features

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.util.*
import java.util.UUID

/**
 * UUIDCallId plugin configuration.
 */
@KtorDsl
class UUIDCallId {

    /** Configures which header should be checked for Call Id value. */
    var header: String = HttpHeaders.XCorrelationId

    companion object {

        /** Creates UUIDCallId plugin with default configuration. */
        val UUIDCallIdPlugin: ApplicationPlugin<UUIDCallId> = createApplicationPlugin("UUIDCallId", ::UUIDCallId) {
            onCallReceive { call, _ ->
                call.request.headers[pluginConfig.header] ?: UUID.randomUUID().toString()
            }
        }
    }
}
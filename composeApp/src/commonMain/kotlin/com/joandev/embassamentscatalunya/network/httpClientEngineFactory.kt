package com.joandev.embassamentscatalunya.network

import io.ktor.client.engine.HttpClientEngineFactory

expect fun httpClientEngineFactory(): HttpClientEngineFactory<*>
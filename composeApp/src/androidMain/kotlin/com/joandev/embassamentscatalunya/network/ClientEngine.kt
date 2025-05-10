package com.joandev.embassamentscatalunya.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

actual fun httpClientEngineFactory(): HttpClientEngineFactory<*> = Android

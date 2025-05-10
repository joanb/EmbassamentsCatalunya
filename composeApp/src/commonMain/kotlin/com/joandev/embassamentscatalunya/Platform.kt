package com.joandev.embassamentscatalunya

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
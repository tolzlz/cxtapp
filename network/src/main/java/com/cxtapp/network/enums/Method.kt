package com.cxtapp.network.enums

enum class Method(val value: String) {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    ;

    val isGet get() = this == GET
    val isPost get() = this == POST
    val isHead get() = this == HEAD
    val isPut get() = this == PUT
    val isPatch get() = this == PATCH
    val isDelete get() = this == DELETE
}
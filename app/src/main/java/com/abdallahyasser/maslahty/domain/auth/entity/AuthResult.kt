package com.abdallahyasser.maslahty.domain.auth.entity

sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    object EmailNotVerified : AuthResult<Nothing>()
    data class Error(val message: String) : AuthResult<Nothing>()
}

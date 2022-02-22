package com.example.domain.model

/**
 * Data class to encapsulate the domain response from an async call
 */
data class ResponseDomainModel<T>(var responseData: T, var isSuccess: Boolean, var errorMessage: String?)
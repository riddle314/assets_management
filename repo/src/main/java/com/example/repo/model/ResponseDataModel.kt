package com.example.repo.model

/**
 * Data class to encapsulate the domain response from an async call
 */
data class ResponseDataModel<T>(var responseData: T, var isSuccess: Boolean, var errorMessage: String?)
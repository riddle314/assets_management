package com.example.assetmanagement.domain.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DemoRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataAnalysisRepository

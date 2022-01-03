package com.example.assetmanagement.data.data_analysis_repo.di

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

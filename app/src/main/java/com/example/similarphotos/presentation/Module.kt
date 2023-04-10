package com.example.similarphotos.presentation

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
}
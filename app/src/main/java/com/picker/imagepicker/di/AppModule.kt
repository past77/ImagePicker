package com.picker.imagepicker.di

import com.picker.imagepicker.ui.adapter.ImageAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    class AppModule {

        @Provides
        @Singleton
        fun provideAdapter(): ImageAdapter = ImageAdapter()
    }
}
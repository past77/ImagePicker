package com.picker.imagepicker.di

import android.content.Context
import androidx.room.Room
import com.picker.imagepicker.data.dao.ImageDao
import com.picker.imagepicker.data.database.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): ImageDatabase {
        return Room.databaseBuilder(
            appContext,
            ImageDatabase::class.java,
            "image-db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesProductDao(imageDatabase: ImageDatabase): ImageDao {
        return imageDatabase.imageDao()
    }

}
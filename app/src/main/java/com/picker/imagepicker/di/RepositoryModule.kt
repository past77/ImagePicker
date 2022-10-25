package com.picker.imagepicker.di

import com.picker.imagepicker.data.repository.ImageRoomRepository
import com.picker.imagepicker.data.repository.ImageRoomRepositoryImpl
import com.picker.imagepicker.data.repository.RemoteFirebaseRepository
import com.picker.imagepicker.data.repository.RemoteFirebaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteRepository(remoteRepository: RemoteFirebaseRepositoryImpl): RemoteFirebaseRepository

    @Binds
    @ViewModelScoped
    abstract fun bindImageRoomRepository(imageRoomRepository: ImageRoomRepositoryImpl): ImageRoomRepository
}

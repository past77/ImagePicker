package com.picker.imagepicker.data.repository

import android.net.Uri
import com.picker.imagepicker.data.model.Image
import com.picker.imagepicker.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface RemoteFirebaseRepository {
    suspend fun uploadImage(uri: Uri, result: (UiState) -> Unit)
    suspend fun getImages(result: (UiState) -> Unit): Flow<List<Image>>
}
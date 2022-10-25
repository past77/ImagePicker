package com.picker.imagepicker.usecases

import android.net.Uri
import com.picker.imagepicker.data.repository.RemoteFirebaseRepository
import com.picker.imagepicker.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UploadImageUsecase @Inject constructor(
    private val remoteFirebaseRepository: RemoteFirebaseRepository
) {

    fun invoke(uri: Uri, result: (UiState) -> Unit) {
        if (uri != Uri.EMPTY) {
            result.invoke(UiState.Loading)
            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                remoteFirebaseRepository.uploadImage(uri, result)
            }
        } else {
            result.invoke(UiState.Failure("Pick image!"))
        }
    }
}
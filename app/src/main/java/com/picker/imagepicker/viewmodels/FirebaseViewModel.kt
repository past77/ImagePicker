package com.picker.imagepicker.viewmodels

import android.net.Uri
import androidx.lifecycle.*
import com.picker.imagepicker.data.model.Image
import com.picker.imagepicker.data.repository.RemoteFirebaseRepository
import com.picker.imagepicker.usecases.UploadImageUsecase
import com.picker.imagepicker.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val remoteFirebaseRepository: RemoteFirebaseRepository,
    private val uploadImageUsecase: UploadImageUsecase
) : ViewModel() {

    var images = MutableLiveData<List<Image>>()

    private val state = MutableLiveData<UiState>()
    val image: LiveData<UiState>
        get() = state

    fun uploadImagesToFirebase(uri: Uri) {
        uploadImageUsecase.invoke(uri) {state.value = it }
    }

    fun getImagesFromFirebase() {
        state.value = UiState.Loading

        images = liveData(Dispatchers.IO) {
            remoteFirebaseRepository.getImages { state.value = it }.collect{
                emit(it)
            }
        } as MutableLiveData<List<Image>>
    }


}
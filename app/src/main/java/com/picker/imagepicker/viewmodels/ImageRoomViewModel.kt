package com.picker.imagepicker.viewmodels

import androidx.lifecycle.ViewModel
import com.picker.imagepicker.data.model.Image
import com.picker.imagepicker.data.repository.ImageRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageRoomViewModel @Inject constructor(
    private val imageRoomRepository: ImageRoomRepository
) : ViewModel() {

    fun deleteTable() = imageRoomRepository.deleteTable()

    fun saveAllImages(images: List<Image>) = imageRoomRepository.insertAllImages(images)

    fun insertNewData(images: List<Image>)= imageRoomRepository.insertNewData(images)
}
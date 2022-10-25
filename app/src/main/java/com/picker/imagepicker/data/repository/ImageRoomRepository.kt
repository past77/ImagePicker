package com.picker.imagepicker.data.repository

import androidx.lifecycle.LiveData
import com.picker.imagepicker.data.model.Image
import javax.inject.Singleton

@Singleton
interface ImageRoomRepository {

    fun deleteTable()

    fun insertImage(image: Image)

    fun insertAllImages(images: List<Image>)

    fun insertNewData(images: List<Image>)

    fun getImagesList(): LiveData<List<Image>>

}
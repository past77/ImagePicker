package com.picker.imagepicker.data.repository

import androidx.lifecycle.LiveData
import com.picker.imagepicker.data.dao.ImageDao
import com.picker.imagepicker.data.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class ImageRoomRepositoryImpl  @Inject constructor(
    private val imageDao: ImageDao
) :ImageRoomRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getImagesList(): LiveData<List<Image>> {
        return imageDao.getAllImages()
    }

    override fun deleteTable() {
        coroutineScope.launch {
            imageDao.clearTable()
        }
    }

    override fun insertImage(image: Image) {
        coroutineScope.launch {
            imageDao.insertImage(image)
        }
    }

    override fun insertAllImages(images: List<Image>) {
        coroutineScope.launch {
            imageDao.insertAllImages(images)
        }
    }

    override fun insertNewData(images: List<Image>) {
        coroutineScope.launch {
            imageDao.deleteAndCreate(images)
        }
    }

}
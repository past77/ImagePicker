package com.picker.imagepicker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.picker.imagepicker.data.model.Image


@Dao
interface ImageDao {

    @Transaction
   suspend fun deleteAndCreate(images: List<Image>) {
        clearTable()
        insertAllImages(images)
    }

    @Insert(onConflict = REPLACE)
    suspend fun insertImage(image: Image)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllImages(images: List<Image>)

    @Query("SELECT * FROM image_table")
    fun getAllImages(): LiveData<List<Image>>

    @Query("DELETE FROM image_table")
    suspend fun clearTable()
}
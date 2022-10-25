package com.picker.imagepicker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picker.imagepicker.data.dao.ImageDao
import com.picker.imagepicker.data.model.Image

@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {

    abstract fun imageDao(): ImageDao
//    companion object {
//        private lateinit var INSTANCE: ImageDatabase
//
//        fun getInstance(context: Context): ImageDatabase {
//            INSTANCE = buildRoomDB(context)
//            return INSTANCE
//        }
//
//        private fun buildRoomDB(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                ImageDatabase::class.java,
//                "images-db"
//            )
//                .fallbackToDestructiveMigration()
//                .build()
//    }
}
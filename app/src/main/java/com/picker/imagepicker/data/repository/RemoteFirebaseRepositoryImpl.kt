package com.picker.imagepicker.data.repository

import android.net.Uri
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.picker.imagepicker.data.model.Image
import com.picker.imagepicker.utils.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteFirebaseRepositoryImpl @Inject constructor(
    private val imageRoomRepository: ImageRoomRepository
) : RemoteFirebaseRepository {

    @Inject
    lateinit var firebaseFirestore: FirebaseFirestore

    @Inject
    lateinit var storageReference: StorageReference

    override suspend fun uploadImage(uri: Uri, result: (UiState) -> Unit) {
        val imageRef: StorageReference =
            storageReference.child(System.currentTimeMillis().toString())
        imageRef.putFile(uri).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.isComplete) {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val image = Image(imageURL = uri.toString())
                        firebaseFirestore.collection("images")
                            .add(image)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    imageRoomRepository.insertImage(image)
                                }
                                result.invoke(UiState.Success("Image was uploaded!"))
                            }
                    }.addOnFailureListener {
                        result.invoke(UiState.Failure("Failed when upload image!"))
                    }
                }
            }
        }
    }

    override suspend fun getImages(result: (UiState) -> Unit): Flow<List<Image>> = callbackFlow {
        val eventDocument = firebaseFirestore
            .collection("images")

        val subscription = eventDocument.addSnapshotListener { snapshot, _ ->
            val imagesList = mutableListOf<Image>()
            snapshot?.documentChanges?.forEach { documentChange ->
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val image = documentChange.document.toObject(Image::class.java)
                    imagesList.add(image)
                }
                trySend(imagesList)
            }
            result.invoke(UiState.Success("Images loaded!"))
        }
        awaitClose { subscription.remove() }
    }
}
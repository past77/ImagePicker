package com.picker.imagepicker.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.picker.imagepicker.R
import com.picker.imagepicker.databinding.ImagePickerFragmentBinding
import com.picker.imagepicker.utils.UiState
import com.picker.imagepicker.utils.hide
import com.picker.imagepicker.utils.show
import com.picker.imagepicker.utils.toast
import com.picker.imagepicker.viewmodels.FirebaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private lateinit var binding: ImagePickerFragmentBinding
    private var mImageURI: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImagePickerFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = binding.mainActivityImageView

        val firebaseViewModel: FirebaseViewModel by viewModels()

        val pickImagesFromGallery: ActivityResultLauncher<String> = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                mImageURI = uri
                imageView.setImageURI(uri)
            } else {
                toast("No image was picked")
            }
        }

        with(binding) {
            mainActivityImageView.setOnClickListener {
                pickImagesFromGallery.launch("image/*")
            }
            mainActivityViewAllBtn.setOnClickListener {
                findNavController().navigate(R.id.action_imagePickerFragment_to_imageViewFragment)
            }
            mainActivityUploadBtn.setOnClickListener {
                firebaseViewModel.uploadImagesToFirebase(mImageURI)
            }
        }

        firebaseViewModel.image.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.message)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    binding.mainActivityImageView.setImageResource(R.drawable.upload)
                    mImageURI = Uri.EMPTY
                    toast(state.message)
                }
            }
        }
    }
}
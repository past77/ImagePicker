package com.picker.imagepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picker.imagepicker.databinding.ImageViewFragmentBinding
import com.picker.imagepicker.ui.adapter.ImageAdapter
import com.picker.imagepicker.utils.UiState
import com.picker.imagepicker.utils.hide
import com.picker.imagepicker.utils.show
import com.picker.imagepicker.utils.toast
import com.picker.imagepicker.viewmodels.FirebaseViewModel
import com.picker.imagepicker.viewmodels.ImageRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageViewFragment : Fragment() {

    lateinit var binding: ImageViewFragmentBinding
    private val imageRoomViewModel: ImageRoomViewModel by viewModels()
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    @Inject
    lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageViewFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        firebaseViewModel.getImagesFromFirebase()

        observeData()
    }

    private fun observeData() {
        firebaseViewModel.images.observe(viewLifecycleOwner)
        { images ->
            adapter.submitList(images)
            imageRoomViewModel.saveAllImages(images)
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
                    toast(state.message)
                }
            }
        }
    }
}
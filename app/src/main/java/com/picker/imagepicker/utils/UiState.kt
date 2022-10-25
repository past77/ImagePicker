package com.picker.imagepicker.utils

sealed class UiState {
    object Loading: UiState()
    data class Success(val message: String?): UiState()
    data class Failure(val message: String?): UiState()
}
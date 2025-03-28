package com.adrifernandev.marsroverchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.usecases.NavigateRoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigateRoverUseCase: NavigateRoverUseCase
) : ViewModel() {

    data class UIState(
        val initialRover: Rover? = null,
        val instructions: Instructions? = null,
        val finalRover: Rover? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    private fun onRequestRoverInstructions() {
        viewModelScope.launch {
            try {
                setLoadingState(true)
                val roverNavigationResult = navigateRoverUseCase()

                with(roverNavigationResult) {
                    _uiState.value = _uiState.value.copy(
                        initialRover = this.initialRover,
                        instructions = this.instructions,
                        finalRover = this.finalRover
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
            setLoadingState(false)
        }
    }

    private fun setLoadingState(state: Boolean) {
        _uiState.value = _uiState.value.copy(
            isLoading = state
        )
    }
}
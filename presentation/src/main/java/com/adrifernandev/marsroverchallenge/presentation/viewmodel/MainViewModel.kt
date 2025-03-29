package com.adrifernandev.marsroverchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.toCommandString
import com.adrifernandev.marsroverchallenge.domain.usecases.NavigateRoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigateRoverUseCase: NavigateRoverUseCase
) : ViewModel() {

    data class UIState(
        val initialRover: Rover? = null,
        val instructions: String? = null,
        val finalRover: Rover? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class UIEvent {
        data object OnRequestRoverInstructions : UIEvent()
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnRequestRoverInstructions -> {
                onRequestRoverInstructions()
            }
        }
    }

    private fun onRequestRoverInstructions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            navigateRoverUseCase().collectLatest { result ->
                result.onSuccess { roverNavigationResult ->
                    _uiState.update {
                        it.copy(
                            initialRover = roverNavigationResult.initialRover,
                            instructions = roverNavigationResult.instructions.toCommandString(),
                            finalRover = roverNavigationResult.finalRover,
                            isLoading = false
                        )
                    }
                }.onFailure {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    // TODO: Handle error state
                }
            }
        }
    }
}
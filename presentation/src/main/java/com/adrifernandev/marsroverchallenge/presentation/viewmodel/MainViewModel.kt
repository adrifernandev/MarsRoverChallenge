package com.adrifernandev.marsroverchallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.adrifernandev.marsroverchallenge.domain.usecases.NavigateRoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigateRoverUseCase: NavigateRoverUseCase
) : ViewModel() {

    data class UIState(
        val foo: String = ""
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()


}
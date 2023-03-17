package com.jaegerapps.travelplanner.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaegerapps.travelplanner.domain.GptRepository
import com.jaegerapps.travelplanner.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: GptRepository,
) : ViewModel() {

    var state by mutableStateOf(TestState())
        private set

    val value = mutableStateOf("")

    fun onValueChannge(newValue: String) {
        value.value = newValue
    }

    fun onSend() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.sendSystemSpec()
                .onSuccess {
                    state = state.copy(
                        isLoading = false,
                        responseInfo = it
                    )
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                }

        }
    }
}
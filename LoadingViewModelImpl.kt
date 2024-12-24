package com.capstime.timecapsule.delegates.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LoadingViewModelImpl @Inject constructor(): ViewModel(), LoadingViewModel {

    sealed class LoadingState {
        data object Loading : LoadingState()
        data object Content : LoadingState()
    }

    private suspend fun MutableStateFlow<LoadingState>.content() = emit(LoadingState.Content)
    private suspend fun MutableStateFlow<LoadingState>.loading() = emit(LoadingState.Loading)

    private var _loading: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.Content)

    override val loading: StateFlow<LoadingState> = _loading.asStateFlow()

    override suspend fun loadingOn() {
        _loading.loading()
    }

    override suspend fun loadingOff() {
        _loading.content()
    }
}

interface LoadingViewModel {

    val loading: StateFlow<LoadingViewModelImpl.LoadingState>

    suspend fun loadingOn()

    suspend fun loadingOff()
}

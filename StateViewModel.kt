package com.capstime.timecapsule.delegates.view_models

import androidx.lifecycle.ViewModel
import com.capstime.timecapsule.extentions.emitNewState
import com.capstime.timecapsule.extentions.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.properties.Delegates

class StateViewModelImpl<State> @Inject constructor(): ViewModel(), StateViewModel<State> {

    private lateinit var uiStateProtected: MutableStateFlow<State>

    override val uiState: StateFlow<State> by lazy {
        uiStateProtected.asStateFlow()
    }

    override var state: State? by Delegates.observable(null) {
            _, _, newValue ->
        if (newValue != null)
            uiStateProtected = MutableStateFlow(newValue)
    }

    override fun emitNewState(block: (State) -> State) {
        launch{
            uiStateProtected.emitNewState(block)
        }
    }
}

interface StateViewModel<State> {

    var state: State?

    val uiState: StateFlow<State>

    fun emitNewState(block: (State) -> State)
}

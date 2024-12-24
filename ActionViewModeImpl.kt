package com.capstime.timecapsule.delegates.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class ActionViewModeImpl<Action> @Inject constructor(): ViewModel(), ActionViewModel<Action> {

    private val uiActionProtected: MutableSharedFlow<Action> = MutableSharedFlow(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    override val uiAction: SharedFlow<Action> = uiActionProtected.asSharedFlow()

    override suspend fun sendAction(newAction: Action) = uiActionProtected.emit(newAction)
}

interface ActionViewModel<Action> {
    val uiAction: SharedFlow<Action>
    suspend fun sendAction(newAction: Action)
}

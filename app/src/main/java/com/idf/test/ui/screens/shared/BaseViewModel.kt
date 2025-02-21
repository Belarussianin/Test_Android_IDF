package com.idf.test.ui.screens.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Event : Any, Effect : Any> : ViewModel() {

    abstract val uiState: StateFlow<State>

    private val _effect: Channel<Effect> = Channel()
    val uiEffectFlow: Flow<Effect> = _effect.receiveAsFlow()

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    abstract fun onEvent(event: Event)
}
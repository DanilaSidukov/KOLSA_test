package com.diphrogram.kolsa_test.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<STATE> : ViewModel() {

    private val initialState: STATE by lazy { createInitialState() }
    abstract fun createInitialState(): STATE

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    protected fun setState(update: STATE.() -> STATE) {
        _state.value = _state.value.update()
    }
}
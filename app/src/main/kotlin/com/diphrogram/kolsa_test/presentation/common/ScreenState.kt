package com.diphrogram.kolsa_test.presentation.common

sealed interface ScreenState {
    data object LoadingProcess : ScreenState
    data object LoadingComplete : ScreenState
}
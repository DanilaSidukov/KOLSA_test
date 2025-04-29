package com.diphrogram.kolsa_test.common

sealed interface ScreenState {
    data object LoadingProcess : ScreenState
    data object LoadingComplete : ScreenState
}
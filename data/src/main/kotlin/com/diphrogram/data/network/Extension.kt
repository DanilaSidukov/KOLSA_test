package com.diphrogram.data.network

import com.diphrogram.domain.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal fun <T> flowWithCatch(block: suspend FlowCollector<Response<T>>.() -> Unit): Flow<Response<T>> =
    flow(block).catch { exception ->
        emit(Response.Error(exception.localizedMessage ?: "Unknown error"))
    }
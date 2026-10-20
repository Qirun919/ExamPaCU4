package com.example.exampa.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    protected suspend fun <T> safeApiCall(
        func: suspend () -> T?
    ): T? {

        _loading.value = true

        return try {
            func()

        } catch (e: HttpException) {
            e.printStackTrace()

            _error.emit(
                e.message()
            )

            null

        } catch (e: Exception) {
            e.printStackTrace()

            _error.emit(
                e.message ?: "Something went wrong"
            )

            null

        } finally {
            _loading.value = false
        }
    }
}
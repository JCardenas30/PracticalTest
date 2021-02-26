package com.sunwise.practicaltest.view.vm

import androidx.lifecycle.ViewModel
import com.sunwise.practicaltest.domain.usecases.UserUseCase

class SplashViewModel: ViewModel() {
    private val userUseCase: UserUseCase by lazy { UserUseCase() }

    fun test(result: () -> Unit) = userUseCase.test(result)
    fun active(result: (res: Boolean) -> Unit) = userUseCase.active(result)
}
package com.sunwise.practicaltest.domain.usecases

import com.sunwise.practicaltest.domain.models.User
import com.sunwise.practicaltest.domain.repository.UserRepository

class UserUseCase{
    private val repository: UserRepository by lazy { UserRepository() }

    fun getSession(result: (user: User?) -> Unit) = repository.getSession(result)
    fun active(result: (res: Boolean) -> Unit) = repository.active(result)
    fun login(user: User, result: (user: User?) -> Unit) = repository.login(user, result)
    fun update(user: User, ok: () -> Unit) = repository.update(user, ok)
    fun logout(ok: () -> Unit) = repository.logout(ok)
    fun test(result: () -> Unit) = repository.test(result)
}
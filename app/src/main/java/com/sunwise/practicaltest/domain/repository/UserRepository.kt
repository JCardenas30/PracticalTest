package com.sunwise.practicaltest.domain.repository

import com.sunwise.practicaltest.data.db.dao.UserDao
import com.sunwise.practicaltest.domain.models.User
import kotlinx.coroutines.runBlocking

class UserRepository: BaseRepository() {
    private val dao: UserDao by lazy { database.userDao() }

    fun active(result: (res: Boolean) -> Unit){
        runBlocking {
            result(dao.existSession() != null)
        }
    }

    fun login(user: User, result: (user: User?) -> Unit) {
        runBlocking {
            result(dao.login(user.email, user.password))
        }
    }

    fun update(user: User, ok: () -> Unit){
        runBlocking {
            dao.update(user)
            ok()
        }
    }

    fun logout(ok: () -> Unit){
        runBlocking {
            dao.logout()
            ok()
        }
    }

    fun test(result: () -> Unit){
        val users = mutableListOf<User>()
        for(i in 0..4){
            val user = User("admin$i@admin.com", "Admin$i#")
            users.add(user)
        }
        runBlocking {
            dao.insert(users)
            result()
        }
    }
}
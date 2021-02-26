package com.sunwise.practicaltest.data.db.dao

import androidx.room.*
import com.sunwise.practicaltest.domain.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: List<User>): List<Long>

    @Update
    suspend fun update(user: User)

    @Query("""
        SELECT * FROM User u WHERE u.isActive = 1
    """)
    suspend fun existSession(): User?

    @Query("""
        SELECT * FROM User u WHERE u.email = :email AND u.password = :password
    """)
    suspend fun login(email: String, password: String): User?

    @Query("""
        UPDATE User SET isActive = 0
    """)
    suspend fun logout()
}
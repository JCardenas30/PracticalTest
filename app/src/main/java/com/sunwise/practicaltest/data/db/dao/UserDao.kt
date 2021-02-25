package com.sunwise.practicaltest.data.db.dao

import androidx.room.*
import com.sunwise.practicaltest.domain.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: List<User>): Long

    @Query("""
        SELECT * FROM User u WHERE u.isActive = 1
    """)
    suspend fun existSession(): User?
}
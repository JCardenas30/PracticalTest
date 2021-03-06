package com.sunwise.practicaltest.data.db.dao

import androidx.room.*
import com.sunwise.practicaltest.domain.models.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Pokemon>): List<Long>

    @Query("""
        SELECT * FROM Pokemon
    """)
    suspend fun all(): List<Pokemon>

    @Query("""
        SELECT COUNT(*) FROM Pokemon
    """)
    suspend fun exist(): Int

    @Delete
    suspend fun remove(item: Pokemon)
}
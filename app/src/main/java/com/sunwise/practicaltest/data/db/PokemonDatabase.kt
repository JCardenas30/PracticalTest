package com.sunwise.practicaltest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunwise.practicaltest.data.db.dao.PokemonDao
import com.sunwise.practicaltest.data.db.dao.UserDao
import com.sunwise.practicaltest.domain.models.Pokemon
import com.sunwise.practicaltest.domain.models.User

@Database(entities = [
    Pokemon::class, User::class],
    version = 1, exportSchema = false
)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private const val DB_NAME = "database"
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDatabase::class.java,
                        DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
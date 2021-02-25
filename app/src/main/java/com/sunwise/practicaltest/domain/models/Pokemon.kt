package com.sunwise.practicaltest.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokemon(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name: String = "",
        val url: String = ""
)
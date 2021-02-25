package com.sunwise.practicaltest.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String? = null,
    val password: String? = null,
    val isActive: Boolean = false
)
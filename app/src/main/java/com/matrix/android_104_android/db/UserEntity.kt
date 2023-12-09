package com.matrix.android_104_android.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val userName : String,
    val password : String
)
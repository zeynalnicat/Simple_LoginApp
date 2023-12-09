package com.matrix.android_104_android.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {

    @Insert
    suspend fun insert(userEntity: UserEntity):Long

    @Query("Select Count(*) from users where userName==:username AND password==:password")
    suspend fun checkLogin(username:String, password:String):Int
}
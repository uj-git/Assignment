package com.umang.assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    var name :String,
    @PrimaryKey
    var email : String,
    var number : String,
    var password : String
)

package com.example.newproject_08_2024

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val
    id: Int = 0,
    var name: String,
    var isCompleted: Boolean = false
)
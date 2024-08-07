package com.example.newproject_08_2024

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend fun insert (task: Task)

    @Update
    suspend fun update (task: Task)

    @Delete
    suspend fun delete (task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
        fun getAllTasks(): LiveData<List<Task>>


}
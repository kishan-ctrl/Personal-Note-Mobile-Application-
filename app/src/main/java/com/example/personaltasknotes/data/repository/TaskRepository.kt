package com.example.personaltasknotes.data.repository

import androidx.lifecycle.LiveData
import com.example.personaltasknotes.data.local.db.AppDatabase
import com.example.personaltasknotes.data.local.entity.TaskEntity

class TaskRepository(
    private val db: AppDatabase = AppDatabase.get()
) {
    private val dao = db.taskDao()

    fun observeAllTasks(): LiveData<List<TaskEntity>> = dao.observeAllTasks()

    suspend fun getTaskById(id: Long): TaskEntity? = dao.getTaskById(id)

    suspend fun addTask(title: String, description: String) {
        val now = System.currentTimeMillis()
        dao.insert(
            TaskEntity(
                title = title,
                description = description,
                isCompleted = false,
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun updateTask(task: TaskEntity) {
        dao.update(task.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deleteTask(task: TaskEntity) {
        dao.delete(task)
    }
}
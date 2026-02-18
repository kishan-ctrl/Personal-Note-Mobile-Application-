package com.example.personaltasknotes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaltasknotes.data.local.entity.TaskEntity
import com.example.personaltasknotes.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repo: TaskRepository = TaskRepository()
) : ViewModel() {

    val tasks = repo.observeAllTasks()

    fun toggleCompleted(task: TaskEntity, completed: Boolean) {
        viewModelScope.launch {
            repo.updateTask(task.copy(isCompleted = completed))
        }
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch {
            repo.deleteTask(task)
        }
    }
}
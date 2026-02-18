package com.example.personaltasknotes.ui.edit

import androidx.lifecycle.*
import com.example.personaltasknotes.data.local.entity.TaskEntity
import com.example.personaltasknotes.data.repository.TaskRepository
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    private val repo: TaskRepository = TaskRepository()
) : ViewModel() {

    // Draft text that survives rotation
    val draftTitle = MutableLiveData("")
    val draftDescription = MutableLiveData("")

    private val _loadedTask = MutableLiveData<TaskEntity?>(null)
    val loadedTask: LiveData<TaskEntity?> = _loadedTask

    fun loadIfEditing(taskId: Long) {
        if (taskId <= 0) return
        if (_loadedTask.value != null) return // avoid reloading
        viewModelScope.launch {
            val t = repo.getTaskById(taskId)
            _loadedTask.postValue(t)
            // set drafts only once when loading
            t?.let {
                draftTitle.postValue(it.title)
                draftDescription.postValue(it.description)
            }
        }
    }

    fun save(taskId: Long, isCompleted: Boolean, onDone: () -> Unit) {
        viewModelScope.launch {
            if (taskId <= 0) {
                repo.addTask(draftTitle.value.orEmpty(), draftDescription.value.orEmpty())
            } else {
                val current = repo.getTaskById(taskId)
                if (current != null) {
                    repo.updateTask(
                        current.copy(
                            title = draftTitle.value.orEmpty(),
                            description = draftDescription.value.orEmpty(),
                            isCompleted = isCompleted
                        )
                    )
                }
            }
            onDone()
        }
    }
}
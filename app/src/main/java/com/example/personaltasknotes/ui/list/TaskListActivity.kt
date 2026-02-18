package com.example.personaltasknotes.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasknotes.databinding.ActivityTaskListBinding
import com.example.personaltasknotes.ui.edit.AddEditTaskActivity
import com.example.personaltasknotes.util.Constants

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private val vm: TaskListViewModel by viewModels()

    private val adapter by lazy {
        TaskAdapter(
            onClick = { task ->
                val i = Intent(this, AddEditTaskActivity::class.java)
                i.putExtra(Constants.EXTRA_TASK_ID, task.id)
                startActivity(i)
            },
            onToggle = { task, checked ->
                vm.toggleCompleted(task, checked)
            },
            onDelete = { task ->
                vm.delete(task)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerTasks.adapter = adapter

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }

        vm.tasks.observe(this) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }
    }
}

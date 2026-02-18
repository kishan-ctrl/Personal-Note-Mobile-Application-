package com.example.personaltasknotes.ui.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasknotes.data.local.entity.TaskEntity
import com.example.personaltasknotes.databinding.ItemTaskBinding

class TaskAdapter(
    private val onClick: (TaskEntity) -> Unit,
    private val onToggle: (TaskEntity, Boolean) -> Unit,
    private val onDelete: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskVH>() {

    private var items: List<TaskEntity> = emptyList()

    fun submitList(newItems: List<TaskEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVH(binding)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TaskVH(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskEntity) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description
            binding.cbDone.setOnCheckedChangeListener(null)
            binding.cbDone.isChecked = task.isCompleted

            // UI effect for completed tasks
            binding.tvTitle.paintFlags = if (task.isCompleted) {
                binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.root.setOnClickListener { onClick(task) }

            binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                onToggle(task, isChecked)
            }

            binding.btnDelete.setOnClickListener { onDelete(task) }
        }
    }
}

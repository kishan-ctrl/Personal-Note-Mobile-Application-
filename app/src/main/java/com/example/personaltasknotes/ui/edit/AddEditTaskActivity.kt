package com.example.personaltasknotes.ui.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.example.personaltasknotes.databinding.ActivityAddEditTaskBinding
import com.example.personaltasknotes.util.Constants
import com.example.personaltasknotes.util.Validators




class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding

    private val vm: AddEditTaskViewModel by viewModels()

    private var taskId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskId = intent.getLongExtra(Constants.EXTRA_TASK_ID, -1)

        // Load task if editing
        vm.loadIfEditing(taskId)

        // Observe draft -> set text (only if needed)
        vm.draftTitle.observe(this) { newValue ->
            if (binding.etTitle.text?.toString() != newValue) {
                binding.etTitle.setText(newValue)
                binding.etTitle.setSelection(newValue.length)
            }
        }
        vm.draftDescription.observe(this) { newValue ->
            if (binding.etDescription.text?.toString() != newValue) {
                binding.etDescription.setText(newValue)
                binding.etDescription.setSelection(newValue.length)
            }
        }
        // Keep drafts updated (rotation safe)
        binding.etTitle.addTextChangedListener(simpleWatcher { vm.draftTitle.value = it })
        binding.etDescription.addTextChangedListener(simpleWatcher { vm.draftDescription.value = it })

        // If editing, show checkbox for completed
        binding.cbCompleted.visibility = if (taskId > 0) android.view.View.VISIBLE else android.view.View.GONE

        vm.loadedTask.observe(this) { task ->
            if (task != null) {
                binding.toolbarTitle.text = "Edit Task"
                binding.cbCompleted.isChecked = task.isCompleted
            } else {
                binding.toolbarTitle.text = "Add Task"
            }
        }

        binding.btnSave.setOnClickListener {
            val title = vm.draftTitle.value.orEmpty()
            val desc = vm.draftDescription.value.orEmpty()

            // Secure coding practice #1: validate input
            val titleErr = Validators.validateTitle(title)
            val descErr = Validators.validateDescription(desc)

            if (titleErr != null) {
                showSnack(titleErr)
                return@setOnClickListener
            }
            if (descErr != null) {
                showSnack(descErr)
                return@setOnClickListener
            }

            vm.save(
                taskId = taskId,
                isCompleted = binding.cbCompleted.isChecked
            ) {
                runOnUiThread { finish() }
            }
        }

        binding.btnBack.setOnClickListener { finish() }


    }

    private fun showSnack(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun simpleWatcher(onText: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onText(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
package com.example.newproject_08_2024

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(
    private val onTaskUpdated: (Task) -> Unit,
    private val onTaskDeleted: (Task) -> Unit,
    private val onNewTaskEntered: (String) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TasksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return TaskViewHolder(view)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bind(currentTask)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val editTextTaskName: EditText = itemView.findViewById(R.id.editTextTaskName)
        private val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        fun bind(task: Task) {
            if (task.name.isNotEmpty()) {
                editTextTaskName.setText(task.name)
            } else {
                editTextTaskName.text = null // Mostrar apenas o hint se a tarefa estiver vazia
            }

            // Listener para quando o usuário termina de editar a tarefa
            editTextTaskName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val newTaskName = editTextTaskName.text.toString()
                    if (newTaskName.isNotEmpty() && task.name != newTaskName) {
                        task.name = newTaskName
                        onTaskUpdated(task)
                    }
                }
            }

            // Listener para marcar a tarefa como concluída
            checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
                onTaskUpdated(task)
            }

            // Listener para deletar a tarefa
            buttonDelete.setOnClickListener {
                onTaskDeleted(task)
            }
        }
    }

    class TasksComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
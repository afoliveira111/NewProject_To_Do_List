package com.example.newproject_08_2024

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val buttonAddTask: Button = findViewById(R.id.buttonAddTask)

        adapter = TaskAdapter(onTaskUpdated = { task ->
            taskViewModel.update(task)
        }, onTaskDeleted = { task ->
            taskViewModel.delete(task)
        }, onNewTaskEntered = { newTaskName ->
            val newTask = Task(name = newTaskName)
            taskViewModel.insert(newTask)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observando as tarefas e atualizando a lista
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            Log.d("MainActivity", "Task list updated: ${tasks.size} tasks")
            tasks?.let {
                adapter.submitList(it.toList()) // Atualiza a lista de tarefas
            }
        })

        buttonAddTask.setOnClickListener {
            Log.d("MainActivity", "Add Task button clicked")
            val newTask = Task(name = "") // Tarefa inicial vazia
            taskViewModel.insert(newTask) // Insere a nova tarefa no banco de dados
            Log.d("MainActivity", "Task inserted: $newTask")
        }
    }
}
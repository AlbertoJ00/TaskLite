package com.example.tasklite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.tasklite.data.AppDatabase
import com.example.tasklite.navigation.NavGraph
import com.example.tasklite.repository.TaskRepository
import com.example.tasklite.ui.theme.TaskLiteTheme
import com.example.tasklite.viewmodel.TaskViewModel
import com.example.tasklite.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        val factory = TaskViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TaskLiteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}

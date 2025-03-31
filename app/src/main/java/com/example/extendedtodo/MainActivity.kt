package com.example.extendedtodo

import android.graphics.Outline
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.extendedtodo.ui.theme.ExtendedToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExtendedToDoTheme {
                ToDoApp()
            }
        }
    }
}

@Composable
fun ToDoItem(task: String, onRemove: () -> Unit, onShowDialog: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = task)
            Button(onClick = onShowDialog ) { Text("Edit") }
            Button(onClick = onRemove) { Text("Remove") }
        }
    }
}
@Composable
fun ToDoScreen(
    currentToDo: String,
    currentEdit: String,
    toDoList: List<String>,
    onToDoChange: (String) -> Unit,
    onEditChange: (String) -> Unit,
    onAddToDo: () -> Unit,
    onEditToDo: (String) -> Unit,
    onRemoveToDo: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var editValue by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Extended To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        OutlinedTextField(
            value = currentToDo,
            onValueChange = onToDoChange,
            label = { Text(text="New Task") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onAddToDo()
                defaultKeyboardAction(ImeAction.Done)
            })
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = onAddToDo){Text("Add Task")}
        LazyColumn {
            items(toDoList) {
                    task -> ToDoItem(
                        task = task,
                        onRemove = { onRemoveToDo(task) },
                        onShowDialog = {
                            showDialog = true
                            editValue = task
                        }
                    )
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Edit Task") },
                text = {
                    Column {
                        Text("Previous name: $editValue")
                        OutlinedTextField(
                            value = currentEdit,
                            onValueChange = onEditChange,
                            label = { Text("Task Name") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onEditToDo(editValue)
                        showDialog = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
@Composable
fun ToDoApp() {
    var currentToDo by remember { mutableStateOf("") }
    val toDoList = remember { mutableStateListOf<String>() }
    var currentEdit by remember { mutableStateOf("") }

    ToDoScreen(
        currentToDo = currentToDo,
        currentEdit = currentEdit,
        toDoList = toDoList,
        onToDoChange = {currentToDo = it},
        onEditChange = {currentEdit = it},
        onAddToDo = {
            if (currentToDo.isNotBlank()) {
                toDoList.add(currentToDo)
                currentToDo = ""
            }
        },
        onEditToDo = {toDo ->
            if (currentEdit.isNotBlank()) {
                val index = toDoList.indexOf(toDo)
                toDoList[index] = currentEdit
                currentEdit = ""
            }
        },
        onRemoveToDo = {toDo -> toDoList.remove(toDo)},
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ExtendedToDoTheme {
        ToDoScreen(
            currentToDo = "",
            currentEdit = "",
            toDoList = listOf("Be great", "Handle it", "Keep going"),
            onToDoChange = {},
            onEditChange = {},
            onAddToDo = {},
            onEditToDo = {},
            onRemoveToDo = {},
        )
    }
}
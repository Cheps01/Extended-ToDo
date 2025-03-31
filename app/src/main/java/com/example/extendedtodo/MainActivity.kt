package com.example.extendedtodo

import android.graphics.Outline
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
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
fun NewElementForm(
    currentToDo: String,
    onToDoChange: (String) -> Unit,
    onAddToDo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Button(onClick = onAddToDo){Text("Add Task")}
    }
}
@Composable
fun ToDoItem(task: String, onRemove: () -> Unit) {
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
            Button(onClick = onRemove) { Text("Remove") }
        }
    }
}
@Composable
fun ToDoList(toDoList: List<String>, onRemoveToDo: (String) -> Unit) {
    LazyColumn {
        items(toDoList) {
            task -> ToDoItem(task = task, onRemove = { onRemoveToDo(task) })
        }
    }
}
@Composable
fun ToDoApp() {
    var currentToDo by remember { mutableStateOf("") }
    var toDoList = remember { mutableStateListOf<String>() }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.heightIn(8.dp))
        Text(
            text = "Extended ToDo List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 10.dp)
            )
        NewElementForm(
            currentToDo = currentToDo,
            onToDoChange = {currentToDo = it},
            onAddToDo = {
                if (currentToDo.isNotBlank()) {
                    Log.d("ToDoApp", "Adding task: $currentToDo")
                    toDoList.add(currentToDo)
                    currentToDo = ""
                }
            }
        )
        ToDoList(
            toDoList = toDoList,
            onRemoveToDo = {toDo -> toDoList.remove(toDo)}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ToDoApp()
}
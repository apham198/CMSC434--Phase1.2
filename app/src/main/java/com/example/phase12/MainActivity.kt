package com.example.phase12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phase12.ui.theme.Phase12Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                    Phase12Theme {
                        MainScreen()
                    }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Text", "Choices", "ToDo", "Profile", "Colors", "Lower Right")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTabIndex) {
                0 -> TextTab()
                1 -> ChoicesTab()
                2 -> ToDoTab()
                3 -> ProfileTab()
                4 -> ColorsTab()
                5 -> LowerRightTab()
            }
        }
    }
}

@Composable
fun TextTab() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Text("A long text content that the user can scroll", Modifier.padding(8.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoicesTab() {
    val radioOptions = listOf("Red", "Blue")
    val (selectedRadioOption, onRadioOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (selectedDropdownOption, onDropdownOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val (displayText, setDisplayText) = remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Hat color to place on Player One's head:")
        radioOptions.forEach { option ->
            Row(Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = (option == selectedRadioOption),
                    onClick = { onRadioOptionSelected(option) }
                )
                Text(
                    text = option,
                    modifier = Modifier
                        .clickable(onClick = { onRadioOptionSelected(option) })
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Hat color to place on Player Two's head:")
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                setExpanded(!expanded)
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedDropdownOption,
                onValueChange = { },
                label = { Text("Color") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // Additional placement logic can be handled here if necessary
                    }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) }
            ) {
                radioOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onDropdownOptionSelected(selectionOption)
                            setExpanded(false)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                setDisplayText("Player One: $selectedRadioOption, Player Two: $selectedDropdownOption")
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Confirm Choices")
        }

        Spacer(Modifier.height(16.dp))

        if (displayText.isNotEmpty()) {
            Text(displayText, Modifier.padding(vertical = 8.dp))
        }
    }
}




@Composable
fun ToDoTab() {
}

@Composable
fun ProfileTab() {
}

@Composable
fun ColorsTab() {
}

@Composable
fun LowerRightTab() {
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Phase12Theme {
        MainScreen()
    }
}
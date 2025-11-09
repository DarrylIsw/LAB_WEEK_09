package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.PrimaryTextButton
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

//Declare a data class called Student
data class Student(
    var name: String
)

//Previously we extend AppCompatActivity,
//now we extend ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            //You can check out the LAB_WEEK_09Theme inside Theme.kt
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from theme
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the wholescreen
                            modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the backgroundcolor
                            //and set it as the color of the surface
                            color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

// Here, we create a composable function called Home.
// Home is the parent composable that manages state and passes it to HomeContent.
@Composable
fun Home() {
    // We use mutableStateListOf to make the list mutable.
    // This is so that we can add or remove items from the list.
    // If you're still confused, this is basically the same concept as using useState in React.
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // Here, we create a mutable state of Student.
    // This is so that we can get and update the value of the input field.
    var inputField by remember { mutableStateOf(Student("")) }

    // We call the HomeContent composable.
    // Here, we pass:
    // - listData to show the list of items inside HomeContent
    // - inputField to show the input field value inside HomeContent
    // - A lambda function to update the value of the inputField
    // - A lambda function to add the inputField to the listData
    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { input ->
            inputField = inputField.copy(name = input)
        },
        onButtonClick = {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("") // reset input field
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    // LazyColumn displays a list of items lazily (RecyclerView replacement)
    LazyColumn {
        // Here, we use item to display a single section inside LazyColumn
        item {
            Column(
                // Modifier.padding(16.dp) adds padding around the Column
                // You can also use Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                // or Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                // Align Column content horizontally
                horizontalAlignment = Alignment.CenterHorizontally
                // You can also use verticalArrangement = Arrangement.Center to center vertically
            ) {
                // Display a title using custom UI element
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item)
                )

                // Text input field for new item
                TextField(
                    // Set the current value of the input field
                    value = inputField.name,
                    // Specify keyboard type
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    // Define what happens when the value changes
                    onValueChange = {
                        // Call the onInputValueChange lambda
                        // and pass the updated input text
                        onInputValueChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Button to add the item
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click)
                ) {
                    onButtonClick()
                }
            }
        }

        // Here, we use items() to display the list inside LazyColumn
        // This is the RecyclerView replacement
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display each item using a reusable UI element
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

// Here, we create a composable function called HomeContent.
// HomeContent is used to display the content of the Home composable.
//@Composable
//fun HomeContent(
//    listData: SnapshotStateList<Student>,
//    inputField: Student,
//    onInputValueChange: (String) -> Unit,
//    onButtonClick: () -> Unit
//) {
//    // Here, we use LazyColumn to display a list of items lazily.
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Input and button section
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = stringResource(id = R.string.enter_item))
//
//                // Text input field
//                TextField(
//                    value = inputField.name,
//                    onValueChange = {
//                        // Here, we call the onInputValueChange lambda function
//                        // and pass the value of the input field as a parameter
//                        onInputValueChange(it)
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                )
//
//                // Button to add item
//                Button(
//                    onClick = {
//                        // Call the onButtonClick lambda to add the value to listData
//                        onButtonClick()
//                    },
//                    modifier = Modifier.padding(top = 8.dp)
//                ) {
//                    Text(text = stringResource(id = R.string.button_click))
//                }
//            }
//        }
//
//        // Display list of items inside LazyColumn (RecyclerView replacement)
//        items(listData) { item ->
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 4.dp)
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = item.name)
//            }
//        }
//    }
//}


// Here, we create a preview function of the Home composable.
// This function is specifically used to show a preview of the Home composable.
// This is only for development purposes.
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Home()
}
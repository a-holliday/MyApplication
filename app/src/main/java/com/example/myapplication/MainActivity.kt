package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                ) {
                    MainUI()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI( modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var randomQuestion : Question? = null

    scope.launch {


        getRandomQuestion()


        var userAnswer by rememberSaveable { mutableStateOf("") }
        val snackbarHostState = remember { SnackbarHostState() }

        Column(
            modifier = modifier,
            Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (randomQuestion != null) {
                randomQuestion?.prompt?.let {
                    Text(
                        text = it,
                        modifier = modifier.align(alignment = Alignment.CenterHorizontally)
                            .padding(25.dp),
                        textAlign = TextAlign.Center


                    )
                }

                TextField(
                    userAnswer,
                    onValueChange = { userAnswer = it },
                    modifier = Modifier.fillMaxWidth().padding(25.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone =
                        {
                            if (randomQuestion?.answer.equals(userAnswer)) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "That's right!"
                                    )
                                }
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Not so!"
                                    )
                                }
                            }
                        }
                    )
                )


                Row(
                    modifier = modifier,

                    ) {


                }
                Button(onClick = {
                    getRandomQuestion()
                }, shape = MaterialTheme.shapes.small) {
                    Text(
                        text = "Next"
                    )
                    Icon(Icons.Default.PlayArrow, contentDescription = "arrow button")
                }


            }
        }
    }
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.fillMaxSize()
    )
}

suspend fun getRandomQuestion() :Question{
    val context: Context = ApplicationProvider.getApplicationContext()
    val questionDao = QuestionDatabase.getDatabase(context).QuestionDAO()
    return questionDao.randomQuestion()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainUI()
    }
}
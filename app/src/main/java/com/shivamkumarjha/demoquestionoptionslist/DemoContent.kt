package com.shivamkumarjha.demoquestionoptionslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shivamkumarjha.demoquestionoptionslist.ui.theme.Purple200

@Composable
fun DemoContent() {
    val questions = remember { mutableStateListOf<QuestionData>() }

    fun handleQuestionDataEvents(events: QuestionDataEvents) {
        when (events) {
            is QuestionDataEvents.AddQuestion -> {
                questions.add(
                    QuestionData(
                        mutableStateOf(""),
                        mutableStateListOf(mutableStateOf(""))
                    )
                )
            }
            is QuestionDataEvents.RemoveQuestion -> {
                questions.removeAt(events.index)
            }
            is QuestionDataEvents.UpdateQuestion -> {
                questions[events.index].question = events.question
            }
            is QuestionDataEvents.AddOption -> {
                questions[events.index].options.add(mutableStateOf(""))
            }
            is QuestionDataEvents.RemoveOption -> {
                questions[events.index].options.removeAt(events.optionIndex)
            }
            is QuestionDataEvents.UpdateOption -> {
                questions[events.index].options[events.optionIndex] = events.option
            }
        }
    }

    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                enabled = if (questions.isEmpty()) true else questions.all { it.question.value.isNotEmpty() },
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    handleQuestionDataEvents(QuestionDataEvents.AddQuestion)
                }
            ) {
                Text("Add question", color = Color.White)
            }
        }
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(questions) { index, questionData ->
                QuestionView(
                    index,
                    questionData,
                    questionDataEvents = { handleQuestionDataEvents(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }

    }

}

@Composable
fun QuestionView(
    index: Int,
    questionData: QuestionData,
    questionDataEvents: (QuestionDataEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    val background = Color.White
    val shape = RoundedCornerShape(8.dp)

    Card(modifier = modifier, elevation = 6.dp, shape = RoundedCornerShape(4.dp)) {

        Column {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                enabled = true,
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    questionDataEvents(QuestionDataEvents.RemoveQuestion(index))
                }
            ) {
                Text("Remove question", color = Color.White)
            }

            TextField(
                value = questionData.question.value,
                placeholder = { Text("Type your question ${index + 1}") },
                onValueChange = {
                    questionData.question.value = it
                    questionDataEvents(
                        QuestionDataEvents.UpdateQuestion(
                            index,
                            questionData.question
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = background,
                    cursorColor = MaterialTheme.colors.onSurface,
                    focusedIndicatorColor = Purple200,
                    disabledIndicatorColor = background
                ),
                maxLines = 1,
                textStyle = MaterialTheme.typography.body2,
                modifier = modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape = shape, clip = true)
                    .background(background, shape = shape)
            )

            OptionView(index, questionData, questionDataEvents, Modifier.fillMaxWidth())
        }

    }
}

@Composable
fun OptionView(
    index: Int,
    questionData: QuestionData,
    questionDataEvents: (QuestionDataEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        Text(
            "Options",
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        questionData.options.forEachIndexed { optionIndex, _ ->
            OptionItemView(
                index,
                optionIndex,
                questionData,
                questionDataEvents,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            enabled = true,
            shape = MaterialTheme.shapes.medium,
            onClick = {
                questionDataEvents(QuestionDataEvents.AddOption(index))
            }
        ) {
            Text("Add option", color = Color.White)
        }

    }
}

@Composable
fun OptionItemView(
    index: Int,
    optionIndex: Int,
    questionData: QuestionData,
    questionDataEvents: (QuestionDataEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    val background = Color.White
    val shape = RoundedCornerShape(8.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(6.dp, shape = shape, clip = true)
            .background(background, shape = shape)
    ) {

        TextField(
            value = questionData.options[optionIndex].value,
            placeholder = { Text("Option ${optionIndex + 1}") },
            onValueChange = {
                questionData.options[optionIndex].value = it
                questionDataEvents(
                    QuestionDataEvents.UpdateOption(
                        index,
                        optionIndex,
                        questionData.options[optionIndex]
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = background,
                cursorColor = MaterialTheme.colors.onSurface,
                focusedIndicatorColor = Purple200,
                disabledIndicatorColor = background
            ),
            maxLines = 1,
            textStyle = MaterialTheme.typography.body2,
            modifier = Modifier.weight(1f)
        )

        if (questionData.options.size > 1) {
            IconButton(onClick = {
                questionDataEvents(QuestionDataEvents.RemoveOption(index, optionIndex))
            }) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = null)
            }
        }

    }
}
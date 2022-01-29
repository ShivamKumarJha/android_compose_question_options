package com.shivamkumarjha.demoquestionoptionslist

import androidx.compose.runtime.MutableState

sealed class QuestionDataEvents {
    object AddQuestion : QuestionDataEvents()
    data class RemoveQuestion(val index: Int) : QuestionDataEvents()
    data class UpdateQuestion(
        val index: Int,
        val question: MutableState<String>
    ) : QuestionDataEvents()

    data class AddOption(val index: Int) : QuestionDataEvents()
    data class RemoveOption(val index: Int, val optionIndex: Int) : QuestionDataEvents()
    data class UpdateOption(
        val index: Int,
        val optionIndex: Int,
        val option: MutableState<String>
    ) : QuestionDataEvents()
}
package com.shivamkumarjha.demoquestionoptionslist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class QuestionData(
    var question: MutableState<String>,
    var options: SnapshotStateList<MutableState<String>>
)
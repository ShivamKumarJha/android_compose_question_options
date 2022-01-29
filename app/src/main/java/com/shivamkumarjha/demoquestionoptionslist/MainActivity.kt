package com.shivamkumarjha.demoquestionoptionslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shivamkumarjha.demoquestionoptionslist.ui.theme.DemoQuestionOptionsListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoQuestionOptionsListTheme {
                DemoContent()
            }
        }
    }
}
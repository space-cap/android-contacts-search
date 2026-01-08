package com.ezlevup.contactssearch.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ezlevup.contactssearch.presentation.components.Greeting

@Composable
fun HomeScreen(

) {
    Greeting(name = "Android")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}



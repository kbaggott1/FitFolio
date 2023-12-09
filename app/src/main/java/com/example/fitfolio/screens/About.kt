package com.example.fitfolio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.R

@Composable
fun AboutScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AboutCard()
    }
}

@Composable
fun AboutCard() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        item{
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AboutTitle() // Added AboutTitlse composable
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 16.dp), // Adjust padding as needed
                    color = MaterialTheme.colorScheme.primary
                )
                AboutText()
                ImageRow()
            }
        }
    }
}

@Composable
fun AboutTitle() {
    Text(
        text = "About",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp, // Adjust the font size as needed
        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
        modifier = Modifier
            .padding(vertical = 16.dp)

    )
}

@Composable
fun AboutText() {
    Text(
        text = "At FitFolio, we are dedicated gym goers who wanted a workout app that matches our" +
                " specific needs. After trying other apps, we decided to create FitFolio, the best " +
                "workout application you've ever had the pleasure of using!",
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ImageRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.cristiano),
            contentDescription = "Image of Creator, Cristiano",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(50.dp))
                .padding(4.dp)
                .fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.kevin),
            contentDescription = "Image of Creator, Kevin",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(40.dp))
                .padding(4.dp)
                .fillMaxSize()
        )
    }
}
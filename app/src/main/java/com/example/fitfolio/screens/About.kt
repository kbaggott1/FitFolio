package com.example.fitfolio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                AboutText()
                ImageRow()
            }
        }
    }
}

@Composable
fun AboutText() {
    Text(
        text = " At FitFolio we have always been dedicated gym goers and have wanted a workout app" +
                " that matches our specific needs while we workout. We tried using other workout apps" +
                " but they were always missing at least some of the features we felt were necessary for" +
                " a pleasant exercising experience. It was against this backdrop of lackluster workout" +
                " applications that we decided to create FitFolio, the best workout application you've " +
                " ever had the pleasure of using!",
        color = contentColorFor(MaterialTheme.colorScheme.background),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ImageRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.cristiano),
            contentDescription = "Image of Creator, Cristiano",
            modifier = Modifier
                .size(125.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
        )

        Image(
            painter = painterResource(id = R.drawable.kevin),
            contentDescription = "Image of Creator, Kevin",
            modifier = Modifier
                .size(125.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}
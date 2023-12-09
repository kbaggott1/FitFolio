package com.example.fitfolio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.R

// Main composable for the "Motivation" Screen. Contains all content for the screen
@Composable
fun MotivationScreen() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MotivationHeader()
        WhyWorkoutCard()
        MotivationalQuotes()
    }
}

// Header, has an image and a title
@Composable
fun MotivationHeader(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Top Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            // Image on the left
            Image(
                painter = painterResource(id = R.drawable.strongman),
                contentDescription = "Motivation Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            // Title and subtitle on the right
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Getting Motivated To Workout",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp)
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp), // Adjust padding as needed
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// Card containing motivation for users to read and feel motivated
@Composable
fun WhyWorkoutCard(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Why Workout?",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp, // Adjust the font size as needed
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        )
        Text(text= "Embarking on a regular workout routine isn't just about sculpting your physique; " +
                "it's a commitment to nurturing your overall well-being. Exercise serves as a powerful catalyst for positive change, " +
                "both physically and mentally. When you lace up those sneakers or roll out that yoga mat, you're not just engaging in a " +
                "physical activity – you're investing in your energy, confidence, and resilience. The benefits extend beyond the gym, " +
                "influencing every facet of your life. From heightened mood and improved sleep to increased focus and stress resilience, " +
                "working out becomes a cornerstone for a more vibrant and fulfilling existence. So, whether it's the invigorating rush of " +
                "endorphins or the satisfaction of achieving new milestones, remember: each workout is a step towards a healthier, happier, " +
                "version of yourself.",
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium), vertical = dimensionResource(id = R.dimen.padding_small)),
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

// Card containing a title and a list of motivational quotes
@Composable
fun MotivationalQuotes(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp), // Adjust padding as needed
            color = MaterialTheme.colorScheme.primary
        )

        val motivationalQuotesList = listOf(
            "Strength does not come from the body. It comes from the will.",
            "Your body can stand almost anything. It's your mind that you have to convince.",
            "Fitness is not about being better than someone else; it's about being better than you used to be.",
            "The only bad workout is the one that didn't happen.",
            "Make your body the sexiest outfit you own."
        )

        Text(
            text = "Motivational Quotes",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp, // Adjust the font size as needed
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_small)),
        )

        motivationalQuotesList.forEach { quote ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                // Circle indicator
                Box(
                    modifier = Modifier
                        .size(8.dp) // Adjust the size of the circle as needed
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .align(Alignment.CenterVertically),
                )

                // Spacer for separation
                Spacer(modifier = Modifier.width(8.dp))

                // Quote text
                Text(
                    text = "$quote", // Note the space before the quote to separate it from the circle
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 8.dp) // Adjust the spacing between the circle and the text
                )
            }
        }
    }
}
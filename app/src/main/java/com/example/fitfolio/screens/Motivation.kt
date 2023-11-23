package com.example.fitfolio.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.R

@Composable
fun MotivationScreen() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
        )
        {
            Text(text = "Why work out?", textAlign = TextAlign.Center, fontSize = 30.sp, modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth())
            Text(text= "Embarking on a regular workout routine isn't just about sculpting your physique; " +
                    "it's a commitment to nurturing your overall well-being. Exercise serves as a powerful catalyst for positive change, " +
                    "both physically and mentally. When you lace up those sneakers or roll out that yoga mat, you're not just engaging in a " +
                    "physical activity – you're investing in your energy, confidence, and resilience. The benefits extend beyond the gym, " +
                    "influencing every facet of your life. From heightened mood and improved sleep to increased focus and stress resilience, " +
                    "working out becomes a cornerstone for a more vibrant and fulfilling existence. So, whether it's the invigorating rush of " +
                    "endorphins or the satisfaction of achieving new milestones, remember: each workout is a step towards a healthier, happier, " +
                    "version of yourself.",
                modifier = Modifier.padding(dimensionResource(id= R.dimen.padding_medium,)),
                textAlign = TextAlign.Justify
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            Text(
                text = "Motivational Quotes",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp,
                textAlign = TextAlign.Center
                )

            Text(text = "- Strength does not come from the body. It comes from the will.", modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small)),)
            Text(text = "- Your body can stand almost anything. It's your mind that you have to convince.", modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small)),)
            Text(text = "- Fitness is not about being better than someone else; it's about being better than you used to be.", modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small)),)
            Text(text = "- The only bad workout is the one that didn't happen.", modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small)),)
            Text(text = "- Make your body the sexiest outfit you own.", modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_small)),)
        }
    }


}
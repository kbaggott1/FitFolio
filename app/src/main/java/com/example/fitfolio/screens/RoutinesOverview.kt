package com.example.fitfolio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitfolio.data.Routine
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.R
import com.example.fitfolio.viewmodels.RoutineViewModel

@Composable
fun RoutineOverviewScreen(modifier: Modifier = Modifier, routineViewModel: RoutineViewModel) {
    RoutineList(
        list = routineViewModel.routines,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        handleDelete = {routineViewModel.remove(it)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineItem(name: String, routineDescription: String?, handleDelete: () -> Unit, openRoutine: () -> Unit) {
    var description = routineDescription;

    if(description == null) {
        description = "No description."
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.routine_card_size))
        .padding(dimensionResource(id = R.dimen.padding_tiny)),
        onClick = openRoutine

    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier
                .weight(2f)
                .padding(dimensionResource(id = R.dimen.padding_small))) {
                Text(name, fontSize = 30.sp)
                Text(description, modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            }
            IconButton(
                onClick = handleDelete,
                modifier = Modifier.weight(0.5f).padding(dimensionResource(id = R.dimen.padding_small))) {
                Icon(
                    Icons.Filled.Close, 
                    contentDescription = "Delete Routine", 
                    modifier = Modifier.size(dimensionResource(id = R.dimen.routine_card_delete_button))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineCard(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.add_routine_card_size))
        .padding(dimensionResource(id = R.dimen.padding_tiny)),
        onClick = onClick

    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)) {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "Add routine",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.add_routine_button))
                    .align(Alignment.Center)
            )
        }

    }
}
@Composable
fun RoutineList(
    list: List<Routine>,
    modifier: Modifier,
    handleDelete: (Routine) -> Unit
) {

    LazyColumn(modifier = modifier) {
        items(list) {
            routine -> RoutineItem(
                name = routine.name,
                routineDescription = routine.description,
                handleDelete = { handleDelete(routine) },
                openRoutine = {}
            )
        }
        item {
            AddRoutineCard(onClick = {/* Todo */})
        }

    }
}

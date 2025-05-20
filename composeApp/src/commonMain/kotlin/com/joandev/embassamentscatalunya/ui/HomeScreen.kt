package com.joandev.embassamentscatalunya.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joandev.embassamentscatalunya.data.ReservoirApiModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel { HomeViewModel() }
    val reservoirs by viewModel.reservoirLevels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Catalan Reservoir Levels") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (isLoading && reservoirs.isEmpty()) { // Show loader only if no data yet
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red, // Consider using theme colors
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (reservoirs.isEmpty() && !isLoading) {
                Text(
                    text = "No data available. Check connection or try again later.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(reservoirs) { reservoir ->
                        ReservoirItem(reservoir)
                    }
                }
            }
        }
    }
}

@Composable
fun ReservoirItem(reservoir: ReservoirApiModel) {
    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(reservoir.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Volume: ${reservoir.currentVolumeHm3} hm³")
            Text("Percentage Full: ${reservoir.percentageFull}%")
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "Last Updated: ${reservoir.lastUpdate.substringBefore("T")}",
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f) // Use theme color
            )
        }
    }
}

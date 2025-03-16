package es.usj.mastertsa.cuidameapp.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTopBar(title: String, showDialog:()-> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, color = Color.Black) },
        actions = {
            IconButton(onClick = { showDialog() }, modifier = Modifier.offset(x = (-15).dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        },
    )
}
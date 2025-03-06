package es.usj.mastertsa.cuidameapp.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTopBar(title: String, showDialog:()-> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.Black) },
        actions = {
            IconButton(onClick = { showDialog() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    )

//    if (showDialog) {
//        MedicationAddScreen(onDismiss = { showDialog = false }, onSuccess = {
//            refreshList()
//            showDialog = false
//        })
//    }
}
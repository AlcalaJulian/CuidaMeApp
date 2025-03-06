package es.usj.mastertsa.cuidameapp.ui.shared

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
    message: String? = null,
    ok:() -> Unit, cancel:()-> Unit){
    AlertDialog(
        onDismissRequest = {
            cancel()
        },
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete this ${ message ?: "item" }?") },
        confirmButton = {
            TextButton(
                onClick = {
                    ok()
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    cancel()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
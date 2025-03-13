package es.usj.mastertsa.cuidameapp.ui.indication.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence

@Composable
fun DosageItem(
    dosage: Recurrence,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                DetailRow(modifier = Modifier,"Cantidad:", dosage.quantity.toString())
                DetailRow(modifier = Modifier,"Fecha:", dosage.specificDate.toString())
                DetailRow(modifier = Modifier,"Hora:", dosage.hour)
                DetailRow(modifier = Modifier,"Completado:", if (dosage.completed) "Si" else "No")
            }

            Column(horizontalAlignment = Alignment.End) {
//                Button(
//                    onClick = onComplete,
//                    enabled = !dosage.completed
//                ) {
//                    Text("Complete")
//                }

                Icon(
                    Icons.Default.CheckCircle,
                    tint = if (dosage.completed) Color.Green else Color.DarkGray,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        if (!dosage.completed)
                            onComplete()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Icon(
                    Icons.Default.Delete,
                    tint = Color.Red,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onDelete()
                    }
                )

//                Button(
//                    onClick = onDelete,
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
//                ) {
//                    Text("Delete", color = Color.White)
//                }
            }
        }
    }
}
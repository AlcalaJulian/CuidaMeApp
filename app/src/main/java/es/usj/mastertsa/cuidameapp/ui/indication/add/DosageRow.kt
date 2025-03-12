package es.usj.mastertsa.cuidameapp.ui.indication.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage

@Composable
fun DosageRow(dosage: Dosage, onSelectDosage: (Dosage) -> Unit, onDeleteClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Add some vertical padding for spacing
            .clip(RoundedCornerShape(8.dp)) // Rounded corners for better look
            .background(MaterialTheme.colors.surface) // Background color for row
            .padding(12.dp), // Padding inside the row for better spacing
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Dosage Text
        Text(
            text = "${dosage.quantity} - ${dosage.hour}",
            style = MaterialTheme.typography.body1, // Use body1 style for consistency
            color = MaterialTheme.colors.onSurface, // Text color matching the theme
            modifier = Modifier.weight(1f) // Make text take the available space
                .clickable {
                    onSelectDosage(dosage)
                }
        )

        // Edit Icon with click effect
        IconButton(
            onClick = { onSelectDosage(dosage) },
            modifier = Modifier.size(24.dp) // Set a fixed size for the icon
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Delete Dosage",
                tint = Color.Yellow // Red color for delete action
            )
        }

        // Delete Icon with click effect
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.size(24.dp) // Set a fixed size for the icon
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Dosage",
                tint = Color.Red // Red color for delete action
            )
        }
    }
}
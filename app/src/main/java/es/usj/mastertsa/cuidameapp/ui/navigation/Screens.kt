package es.usj.mastertsa.cuidameapp.ui.navigation

import kotlinx.serialization.Serializable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.twotone.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Serializable
object Home

@Serializable
object MedicationList

@Serializable
data class MedicationDetail(val id: Int)

@Serializable
object IndicationList

@Serializable
data class IndicationDetail(val id: Int)

@Serializable
object PatientList

@Serializable
data class PatientDetail(val id: Int)

sealed class Screens{
    companion object{
        val navRoutes = listOf(
            TopLevelRoute("Patients", PatientList, Icons.Filled.Favorite),
            TopLevelRoute("Indications", IndicationList, Icons.TwoTone.Person),
            TopLevelRoute("Medications", MedicationList, Icons.TwoTone.Person)
        )
    }
}

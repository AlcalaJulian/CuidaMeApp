package es.usj.mastertsa.cuidameapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Serializable
object MedicineList

@Serializable
data class MedicineDetail(val id: Long)

@Serializable
object IndicationList

@Serializable
data class IndicationDetail(val id: Long)

@Serializable
object PatientList

@Serializable
data class PatientDetail(val id: Long)

@Serializable
object Authentication

@Serializable
object Login

@Serializable
object Register

sealed class Screens{
    companion object{
        val navRoutes = listOf(
            TopLevelRoute("Patients", PatientList, Icons.Filled.Favorite),
            TopLevelRoute("Indications", IndicationList, Icons.TwoTone.Person),
            TopLevelRoute("Medicine", MedicineList, Icons.TwoTone.Person)
        )
    }
}

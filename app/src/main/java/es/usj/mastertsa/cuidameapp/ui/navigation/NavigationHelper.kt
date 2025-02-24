package es.usj.mastertsa.cuidameapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.usj.mastertsa.cuidameapp.ui.indication.detail.IndicationDetailScreen
import es.usj.mastertsa.cuidameapp.ui.indication.list.IndicationListScreen
import es.usj.mastertsa.cuidameapp.ui.medication.detail.MedicationDetailScreen
import es.usj.mastertsa.cuidameapp.ui.medication.list.MedicationListScreen
import es.usj.mastertsa.cuidameapp.ui.patient.list.PatientListScreen

@Composable
fun NavigationHelper(navController: NavHostController, modifier: Modifier = Modifier){

        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = modifier
        ){

            composable<PatientList> {
                PatientListScreen { id -> navController.navigate( PatientDetail(id = id)) }
            }
            composable<PatientDetail> {
                MedicationDetailScreen()
            }
            composable<IndicationDetail> {
                IndicationDetailScreen()
            }
            composable<IndicationList> {
                IndicationListScreen { id -> navController.navigate(IndicationDetail(id = id)) }
            }
            composable<MedicationList> {
                MedicationListScreen { id -> navController.navigate(MedicationDetail(id = id)) }
            }
            composable<MedicationDetail> {
                MedicationDetailScreen()
            }
        }
}
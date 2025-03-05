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
import es.usj.mastertsa.cuidameapp.ui.patient.detail.PatientDetailScreen
import es.usj.mastertsa.cuidameapp.ui.patient.list.PatientListScreen

@Composable
fun NavigationHelper(navController: NavHostController, modifier: Modifier = Modifier){

        NavHost(
            navController = navController,
            startDestination = PatientList,
            modifier = modifier
        ){

            composable<PatientList> {
                PatientListScreen ( { id -> navController.navigate( PatientDetail(id = id)) })
            }
            composable<PatientDetail> {
                PatientDetailScreen(){ navController.popBackStack()}
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
                MedicationDetailScreen { navController.popBackStack() }
            }
        }
}
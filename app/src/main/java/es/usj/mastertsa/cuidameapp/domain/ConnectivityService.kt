package es.usj.mastertsa.cuidameapp.domain

interface ConnectivityService {
    fun checkConnectivity(): Boolean
}
package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import kotlinx.coroutines.launch

//TODO: (Ok) Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    //TODO: (Ok) Create live data val for upcoming elections
    val upcomingElections = electionsRepository.upcommingElections

    //TODO: (Ok) Create live data val for saved elections
    val savedElections = electionsRepository.savedElections

    //TODO: (Ok) Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun refreshElections(){
        viewModelScope.launch {
            electionsRepository.refreshElections()
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}
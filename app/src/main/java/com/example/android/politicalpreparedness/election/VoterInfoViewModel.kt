package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class VoterInfoViewModel(application: Application) : AndroidViewModel(application){
    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)
    private val voterInfoRepository = VoterInfoRepository(database)

    //TODO: (Ok) Add live data to hold voter info
    val election = electionsRepository.election
    val voterInfo = voterInfoRepository.voterInfo
    val isSaved = electionsRepository.isSaved

    //TODO: Add var and methods to populate voter info
    fun getVoterInfo(electionId: Int, address: String){
        viewModelScope.launch {
            voterInfoRepository.getVoterInfo(electionId, address)
        }
    }

    //TODO: (Ok) Add var and methods to support loading URLs
    var _url = MutableLiveData<String>()
    fun intentUrl(url: String) {
        _url.value = url
    }

    //TODO: (Ok) Add var and methods to save and remove elections to local database
    fun saveElection(value: Election){
        viewModelScope.launch {
            electionsRepository.saveElection(value)
        }
    }

    suspend fun deleteElection(id: Int){
        viewModelScope.launch {
            deleteElection(id)
        }
    }

    suspend fun getElection(id: Int){
        viewModelScope.launch {
            electionsRepository.getElection(id)
        }
    }

    fun setElection(value: Election){
        viewModelScope.launch {
            electionsRepository.setElection(value)
            electionsRepository.getElection(value.id)
        }
    }
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    // ElectionsRepository init

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}
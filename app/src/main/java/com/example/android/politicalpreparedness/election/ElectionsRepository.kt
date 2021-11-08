package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsRepository(private val database: ElectionDatabase) {
    val savedElections: LiveData<List<Election>> = database.electionDao.getAllElections()

    private val _upcommingElections = MutableLiveData<List<Election>>()
    val upcommingElections: LiveData<List<Election>> get() = _upcommingElections


    val voterInfo =  MutableLiveData<VoterInfoResponse>()
    val representatives =  MutableLiveData<RepresentativeResponse>()
    fun getElection(id: Int) = database.electionDao.getElectionById(id)

    suspend fun getVoterInfo(electionId: Int, address: String) {
        try {
            withContext(Dispatchers.IO) {
                val response = CivicsApi.retrofitService.getVoterInfoAsync(electionId, address).await()
                voterInfo.postValue(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun refreshElections() {
        try {
            withContext(Dispatchers.IO) {
                val electionResponse = CivicsApi.retrofitService.getElectionsAsync().await()
                _upcommingElections.postValue(electionResponse.elections)
                database.electionDao.insertElections(electionResponse.elections)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
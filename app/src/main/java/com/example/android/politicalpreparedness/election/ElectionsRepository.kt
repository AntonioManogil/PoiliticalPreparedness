package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsRepository(val database: ElectionDatabase) {
    val savedElections: LiveData<List<Election>> = database.electionDao.getAllElections()

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>> get() = _upcomingElections

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election> get() = _election

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> get() = _isSaved


    init{
        _isSaved.postValue(false)
    }

    suspend fun getUpcomingElections() {
        try {
            withContext(Dispatchers.IO) {
                val electionResponse = CivicsApi.retrofitService.getElectionsAsync().await()
                _upcomingElections.postValue(electionResponse.elections)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun saveElection(value: Election){
        withContext(Dispatchers.IO) {
            database.electionDao.saveElection(value)
        }
    }

    suspend fun deleteElection(id: Int){
        withContext(Dispatchers.IO) {
            database.electionDao.deleteElection(id)
        }
    }


    suspend fun getElection(id: Int){
        withContext(Dispatchers.IO) {
            try {
                _isSaved.postValue(false)
                var savedElection = database.electionDao.getElectionById(id)
                savedElection.value?.let{
                    _isSaved.postValue(true)
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun setElection(value: Election){
        _election.postValue(value)
    }
}
package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
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

    suspend fun saveElection(value: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.saveElection(value)
            getElection(value.id)
        }
    }

    suspend fun deleteElection(id: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.deleteElection(id)
            _isSaved.postValue(false)
        }
    }

    // flow really simplifies this
    suspend fun getElection(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                database.electionDao.getElectionById(id).onEach{
                    if(it!=null){
                        _isSaved.postValue(true)
                    }else{
                        _isSaved.postValue(false)
                    }
                }.collect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setElection(value: Election) {
        _election.postValue(value)
    }
}
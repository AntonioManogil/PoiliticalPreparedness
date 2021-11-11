package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: (Ok) Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveElection(election: Election)

    //TODO: (Ok) Add select all election query
    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>

    //TODO: (Ok) Add select single election query
    @Query("SELECT * FROM election_table WHERE id=:id")
    fun getElectionById(id: Int): LiveData<Election>

    //TODO: (Ok) Add delete query
    @Query("DELETE FROM election_table where id=:id")
    fun deleteElection(id: Int)

    //TODO: (Ok) Add clear query
    @Query("DELETE FROM election_table")
    fun deleteAllElections()

}
package com.example.android.politicalpreparedness.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

@BindingAdapter("listUpcomingElections")
fun listUpcomingElections(recyclerView: RecyclerView, data: List<Election>?){
    data?.let{
        val adapter = recyclerView.adapter as ElectionListAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("listSavedElections")
fun listSavedElections(recyclerView: RecyclerView, data: List<Election>?){
    data?.let{
        val adapter = recyclerView.adapter as ElectionListAdapter
        adapter.submitList(data)
    }
}
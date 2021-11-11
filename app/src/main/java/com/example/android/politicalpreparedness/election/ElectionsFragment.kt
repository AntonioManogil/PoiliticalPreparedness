package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter

class ElectionsFragment : Fragment() {

    //TODO: (Ok) Declare ViewModel
    private val electionsViewModel: ElectionsViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ElectionsViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: (Ok) Add ViewModel values and create ViewModel
        electionsViewModel.catchUpcomingElections()

        //TODO: (Ok) Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.electionsViewModel = electionsViewModel

        //TODO: Link elections to voter info

        //TODO: (Ok) Initiate recycler adapters
        val upcomingElectionListAdapter = ElectionListAdapter(ElectionListAdapter.ElectionListener {
            findNavController().navigate(
                ElectionsFragmentDirections.actionShowDetail(it)
            )
        })
        binding.upcomingElectionsRecyclerView.adapter = upcomingElectionListAdapter

        val savedElectionListAdapter = ElectionListAdapter(ElectionListAdapter.ElectionListener {
            findNavController().navigate(
                ElectionsFragmentDirections.actionShowDetail(it)
            )
        })
        binding.savedElectionsRecyclerView.adapter = savedElectionListAdapter

        //TODO: (Ok) Populate recycler adapters
        //See utils/BindingAdapters

        return binding.root
    }
    //TODO: Refresh adapters when fragment loads
}
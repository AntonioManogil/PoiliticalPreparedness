package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val args: VoterInfoFragmentArgs by navArgs()

    //TODO: (Ok) Add ViewModel values and create ViewModel
    private val voterInfoViewModel: VoterInfoViewModel by lazy {
        val application = requireNotNull(this.activity).application
        ViewModelProvider(this, VoterInfoViewModelFactory(application))
            .get(VoterInfoViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val election = args.argElection
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        voterInfoViewModel.setElection(election)
        //TODO: Add binding values
        binding.voterInfoViewModel = voterInfoViewModel


        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return binding.root
    }

    //TODO: Create method to load URL intents

}
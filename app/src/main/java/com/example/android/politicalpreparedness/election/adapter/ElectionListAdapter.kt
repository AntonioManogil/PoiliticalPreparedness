package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.FragmentItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val electionListener: ElectionListener) :
    ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    //TODO: (Ok) Create ElectionViewHolder
    class ElectionViewHolder(private var binding: FragmentItemElectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(election: Election) {
            binding.election = election
            binding.executePendingBindings()
        }

        //TODO: (Ok) Add companion object to inflate ViewHolder (from)
        companion object {
            fun from(parent: ViewGroup): ElectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentItemElectionBinding.inflate(layoutInflater, parent, false)
                return ElectionViewHolder(binding)
            }
        }
    }

    //TODO: (Ok) Create ElectionDiffCallback
    companion object ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //TODO: (Ok) Create ElectionListener
    class ElectionListener(val electionListener: (election: Election) -> Unit) {
        fun onClick(election: Election) = electionListener(election)
    }

    //TODO: (Ok) Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.bind(election)
        holder.itemView.setOnClickListener{
            electionListener.onClick(election)
        }
    }
}




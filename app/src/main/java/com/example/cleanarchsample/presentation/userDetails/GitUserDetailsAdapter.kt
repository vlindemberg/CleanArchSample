package com.example.cleanarchsample.presentation.userDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchsample.databinding.RepoListItemBinding
import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData

class GitUserDetailsAdapter(private val repositories: List<GitRepositoriesViewData>) :
RecyclerView.Adapter<GitUserDetailsAdapter.GitRepoViewHolder>() {
    class GitRepoViewHolder(val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val binding = RepoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GitRepoViewHolder(binding)
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        val gitRepo = repositories[position]
        holder.binding.apply {
            name.text = gitRepo.name
        }
    }
}
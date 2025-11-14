package com.example.cleanarchsample.presentation.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cleanarchsample.R
import com.example.cleanarchsample.databinding.UserDetailFragmentBinding
import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData
import com.example.cleanarchsample.presentation.model.GitUserViewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitUserDetailsFragment : Fragment() {

    private var _binding: UserDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: GitUserDetailsFragmentArgs by navArgs()
    private val viewModel: GitUserDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
        loadData()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.states.collect {
                setupLoading(it.isLoading)
                loadUserDetails(it.user)
                loadRepositories(it.gitRepoList)
                loadError(it.errorMessage)
            }
        }
    }

    private fun loadRepositories(repositories: List<GitRepositoriesViewData>) {
        val repoAdapter = GitUserDetailsAdapter(repositories)
        binding.recyclerViewRepository.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repoAdapter
        }
    }

    private fun loadUserDetails(user: GitUserViewData) {
        binding.run {
            name.text = user.name
            login.text = user.login
            fork.text = user.publicRepo.toString()
            Glide.with(pictureUser).load(user.imgUrl).into(pictureUser)
        }
    }

    private fun loadError(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupLoading(loading: Boolean) {
        binding.loading.isVisible = loading
    }

    private fun loadData() {
        viewModel.getUserDetails(args.username)
        viewModel.getRepositories(args.username)
    }

    private fun setupToolbar() {
        binding.toolbar.title = "GitHub"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white, null))
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}

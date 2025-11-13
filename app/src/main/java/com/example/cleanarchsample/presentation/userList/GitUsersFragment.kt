package com.example.cleanarchsample.presentation.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchsample.R
import com.example.cleanarchsample.databinding.UserListFragmentBinding
import com.example.cleanarchsample.presentation.model.GitUserViewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitUsersFragment : Fragment() {

    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GitUserViewModel by viewModels()

    private lateinit var userAdapter: GitUserAdapter
    private lateinit var userList: List<GitUserViewData>

    private val navControl by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
        setupSearch()
        viewModel.getUsers()
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener, OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    setupFilterList(newText)
                    return true
                }
            }
        )
    }

    private fun setupFilterList(text: String?) {
        val filteredList = viewModel.filterList(text, userList)
        if (filteredList.isEmpty()) {
            binding.constraintLayout.visibility = View.GONE
            binding.emptyListContainer.visibility = View.VISIBLE
        } else {
            binding.constraintLayout.visibility = View.VISIBLE
            binding.emptyListContainer.visibility = View.GONE
            userAdapter.setFilteredList(filteredList)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                setupLoading(it.isLoading)
                setupUsers(it.users)
                loadError(it.errorMessage)
            }
        }
    }
    
    private fun setupUsers(users: List<GitUserViewData>) {
        userList = users
        userAdapter = GitUserAdapter(users, onItemClicked = {
            navigateToUserDetails(it.name)
        })
        binding.recyclerView.run { 
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            isClickable = true
        }
    }

    private fun navigateToUserDetails(name: String) {
        TODO("Not yet implemented")
    }

    private fun setupLoading(loading: Boolean) {
        binding.load.isVisible = loading
    }

    private fun loadError(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
           Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupToolbar() {
        binding.toolbar.title = "GitHub"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white, null))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
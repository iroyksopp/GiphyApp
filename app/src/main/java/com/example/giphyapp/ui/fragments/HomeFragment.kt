package com.example.giphyapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.R
import com.example.giphyapp.databinding.FragmentHomeBinding
import com.example.giphyapp.model.utils.simpleScan
import com.example.giphyapp.ui.adapters.GifLoadStateAdapter
import com.example.giphyapp.ui.adapters.PagingAdapter
import com.example.giphyapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModels()


    private val adapter = PagingAdapter(
        clickListener = { launchDetailsScreen(it.originalUrl) },
        deleteClickListener = { viewModel.deleteItem(it) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeToRefresh()
        setupAdapter()
        observeUsers()
        observeLoadState()
        setupSearchField()
        handleScrollingToTopWhenSearching()
    }

    private fun setupAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewLifecycleOwner.lifecycleScope.launch {
            waitForLoad()
            val footerAdapter = GifLoadStateAdapter()
            val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
            binding.recyclerView.adapter = adapterWithLoadState
        }
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.gifsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        val loadStateHolder = GifLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout
        )
        adapter.loadStateFlow
            .onEach {
                loadStateHolder.bind(it.refresh)
            }
            .launchIn(lifecycleScope)
    }


    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private suspend fun waitForLoad() {
        adapter.onPagesUpdatedFlow
            .map { adapter.itemCount }
            .first { it > 0 }
    }

    private fun launchDetailsScreen(imageUrl: String) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, DetailsFragment.newInstance(imageUrl))
            .commit()
    }

    private fun setupSearchField() {
        binding.searchEditText.setOnEditorActionListener { _, action, _ ->
            if(action == EditorInfo.IME_ACTION_DONE) {
                val keyword = binding.searchEditText.text?.toString()?.trim()
                if(keyword.isNullOrBlank().not()) viewModel.setSearchBy(keyword)
            }
            binding.recyclerView.scrollToPosition(0)
            true
        }
    }


private fun handleScrollingToTopWhenSearching() = lifecycleScope.launch {
    //doesn't work correctly because of slow downloading images into imageViews
    getRefreshLoadStateFlow(adapter)
        .simpleScan(count = 2)
        .collectLatest { (previousState, currentState) ->
            if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                binding.recyclerView.scrollToPosition(0)
            }
        }
}

    private fun getRefreshLoadStateFlow(adapter: PagingAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }
}



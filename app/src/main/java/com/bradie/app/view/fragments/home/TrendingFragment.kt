package com.bradie.app.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bradie.app.adapters.ImagesAdapter
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.databinding.FragmentTrendingBinding
import com.bradie.app.utils.DEFAULT_QUERY
import com.bradie.app.utils.Status
import com.bradie.app.utils.ViewStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val imagesAdapter: ImagesAdapter by lazy { ImagesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        homeViewModel.loadData(DEFAULT_QUERY)
        homeViewModel.data.observe(viewLifecycleOwner, ::processData)
    }


    private fun processData(data: ViewStatus<ImagesModel>) {
        binding.resource = data
        when(data.status) {
            Status.SUCCESS -> handleSuccess(data.data)
            Status.ERROR -> handleError()
            Status.LOADING -> handleLoading()
        }
    }

    private fun handleSuccess(data: ImagesModel?) {
        data?.let {
            lifecycleScope.launch(IO) {
                imagesAdapter.submitList(it.hits)
            }
        }
    }

    private fun handleError() {

    }

    private fun handleLoading() {

    }

    private fun setRecyclerView() {
        binding.recyclerViewImages.apply {
            recycledViewPool.setMaxRecycledViews(0, 100)
            setItemViewCacheSize(100)
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

}
package com.bradie.app.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bradie.app.R
import com.bradie.app.adapters.ImagesAdapter
import com.bradie.app.adapters.OnItemClick
import com.bradie.app.adapters.OnMoreOptionsClick
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.databinding.FragmentTrendingBinding
import com.bradie.app.utils.DEFAULT_QUERY
import com.bradie.app.utils.Status
import com.bradie.app.utils.ViewStatus
import com.bradie.app.view.ImageDetailsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrendingFragment : Fragment(), OnItemClick, OnMoreOptionsClick {
    private lateinit var binding: FragmentTrendingBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val imagesAdapter: ImagesAdapter by lazy { ImagesAdapter(this, this) }
    private lateinit var dialog: BottomSheetDialog

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
        setBottomSheet()
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
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = staggeredGridLayoutManager
            recycledViewPool.setMaxRecycledViews(0, 100)
            setItemViewCacheSize(100)
            setHasFixedSize(true)
            adapter = imagesAdapter
        }
    }

    private fun setBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
    }

    override fun onItemClick(position: Int) {
        val hitModel = imagesAdapter.currentList[position]
        startActivity(
            Intent(requireContext(), ImageDetailsActivity::class.java)
                .putExtra("data", hitModel)
        )
    }

    override fun onOptionMenuClick(position: Int) {
        dialog.show()
    }


    override fun onDestroy() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
        super.onDestroy()
    }

}
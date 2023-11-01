package com.example.infobyte.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infobyte.Others.Constants
import com.example.infobyte.Others.Resource
import com.example.infobyte.R
import com.example.infobyte.adapter.stocksAdapter
import com.example.infobyte.data.models.stocksItem
import com.example.infobyte.databinding.FragmentstocksBinding
import com.example.infobyte.ui.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentStocks : Fragment(R.layout.fragmentstocks) {
    private lateinit var binding: FragmentstocksBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var stockadapter: stocksAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentstocksBinding.bind(view)
        setUpRecyclerView()
        viewModel.getAllStocksFromApi(Constants.user_content_key, Constants.lib)

        CoroutineScope(Dispatchers.Main).launch {
            val list=viewModel.getAllStocksFromDb()
//            Log.d("TAG", "${list}")
            stockadapter.differ.submitList(list)
        }

    }
    private fun setUpRecyclerView() {
        stockadapter = stocksAdapter()
        binding.stockrecycler.apply {
            adapter = stockadapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}






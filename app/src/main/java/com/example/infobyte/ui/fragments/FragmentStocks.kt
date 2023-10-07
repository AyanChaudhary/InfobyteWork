package com.example.infobyte.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.infobyte.Others.Constants
import com.example.infobyte.Others.Resource
import com.example.infobyte.R
import com.example.infobyte.databinding.FragmentstocksBinding
import com.example.infobyte.ui.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentStocks : Fragment(R.layout.fragmentstocks) {
    private lateinit var binding: FragmentstocksBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentstocksBinding.bind(view)

        viewModel.getAllStocks(Constants.user_content_key,Constants.lib)

        viewModel.uiStateForStocks.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    Log.d("TAG", "${it.data} ")
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    // show progress bar
                }

            }
        })
    }

}
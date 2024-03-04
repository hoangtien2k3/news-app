package com.hoangtien2k3.news_app.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.ui.main.MainActivity
import com.hoangtien2k3.news_app.ui.NewsAdapter
import com.hoangtien2k3.news_app.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.hoangtien2k3.news_app.databinding.FragmentSearchNewsBinding
import com.hoangtien2k3.news_app.utils.Constants

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var viewModel: SearchNewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding
    private val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)
        viewModel = (requireActivity() as MainActivity).viewModel

        setupRecyclerView()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }

//            findNavController().navigate(
//                R.id.action_searchNewsFragment_to_articleFragment,
//                bundle
//            )
        }

        binding.toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.business -> viewModel.searchNews("business")
                    R.id.science -> viewModel.searchNews("science")
                    R.id.health -> viewModel.searchNews("health")
                    R.id.sports -> viewModel.searchNews("sports")
                }
            } else {
                if (binding.toggleGroup.checkedButtonId == View.NO_ID) {
                    viewModel.searchNews("entertainment")
                }
            }
        }

        var job: Job? = null
        binding.etSearchNews.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_TIME_DELAY)

                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBarSearchNews.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarSearchNews.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.adapter = newsAdapter
        binding.rvSearchNews.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding object when the view is destroyed
//        binding = null
    }
}


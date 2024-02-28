package com.hoangtien2k3.news_app.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.activity.MainActivity
import com.hoangtien2k3.news_app.adapter.NewsAdapter
import com.hoangtien2k3.news_app.utils.Resource
import com.hoangtien2k3.news_app.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.hoangtien2k3.news_app.utils.Constants

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var toggleGroup: MaterialButtonToggleGroup
    private lateinit var etSearchNews: EditText
    private lateinit var progressBarSearchNews: ProgressBar
    private lateinit var rvSearchNews: RecyclerView
    private val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupViews(view)
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

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.business -> viewModel.searchNews("business")
                    R.id.science -> viewModel.searchNews("science")
                    R.id.health -> viewModel.searchNews("health")
                    R.id.sports -> viewModel.searchNews("sports")
                }
            } else {
                if (toggleGroup.checkedButtonId == View.NO_ID) {
                    viewModel.searchNews("entertainment")
                }
            }
        }

        var job: Job? = null
        etSearchNews.addTextChangedListener { editable ->
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
        progressBarSearchNews.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBarSearchNews.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.adapter = newsAdapter
        rvSearchNews.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupViews(view: View) {
        toggleGroup = view.findViewById(R.id.toggleGroup)
        etSearchNews = view.findViewById(R.id.etSearchNews)
        progressBarSearchNews = view.findViewById(R.id.progressBarSearchNews)
        rvSearchNews = view.findViewById(R.id.rvSearchNews)
    }

}

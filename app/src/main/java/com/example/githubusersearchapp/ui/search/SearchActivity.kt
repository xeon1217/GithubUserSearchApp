package com.example.githubusersearchapp.ui.search

import android.content.Intent
import android.view.KeyEvent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.databinding.ActivitySearchUserBinding
import com.example.githubusersearchapp.ui.base.BaseActivity
import com.example.githubusersearchapp.ui.favorite.FavoriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<ActivitySearchUserBinding>() {
    private val searchUserViewModel: SearchViewModel by viewModel()

    override fun initToolbar() {
        setSupportActionBar(binding.searchToolbar)
        supportActionBar?.apply {
            setTitle(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun bindingAfter() {
        initViewModel()
        initRecyclerView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search_user
    }

    private fun search(query: String) {
        searchUserViewModel.searchUsers(query)
        binding.searchEditText.setText("")
    }

    private fun initViewModel() {
        binding.viewModel = searchUserViewModel

        searchUserViewModel.apply {
            message.observe(this@SearchActivity, Observer { msg ->
                showToast(msg)
            })
        }

        binding.apply {
            favoriteButton.setOnClickListener {
                startActivity(Intent(this@SearchActivity, FavoriteActivity()::class.java))
            }

            searchButton.setOnClickListener {
                search(binding.searchEditText.text.toString())
            }

            searchEditText.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == 1) {
                    search(binding.searchEditText.text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun initRecyclerView() {
        binding.searchUserRecyclerView.let { recyclerView ->
            recyclerView.adapter = RecyclerViewAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager
                    val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    //화면에 보이는 리스트의 view의 position이 itemCount(리스트 총 갯수)와 같다면 리스트의 끝에 도달했다고 판단
                    if (lastVisibleItem == layoutManager.findLastVisibleItemPosition()) {
                        searchUserViewModel.loadUsers()
                    }
                }
            })
        }
    }
}
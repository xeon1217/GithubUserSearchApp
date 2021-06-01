package com.example.githubusersearchapp.ui.favorite

import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.databinding.ActivityFavoriteUserBinding
import com.example.githubusersearchapp.ui.base.BaseActivity
import com.example.githubusersearchapp.ui.search.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class FavoriteActivity : BaseActivity<ActivityFavoriteUserBinding>() {
    private val favoriteUserViewModel: FavoriteViewModel by viewModel()

    override fun initToolbar() {
        setSupportActionBar(binding.favoriteToolbar)
        supportActionBar?.apply {
            setTitle(R.string.favorite_str)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun bindingAfter() {
        initViewModel()
        initRecyclerView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_favorite_user
    }

    override fun onResume() {
        super.onResume()
        favoriteUserViewModel.getFavoriteList()
    }

    fun initViewModel() {
        binding.viewModel = favoriteUserViewModel

        favoriteUserViewModel.apply {
            message.observe(this@FavoriteActivity, Observer { msg ->
                showToast(msg)
            })
        }

        binding.apply {
            searchEditText.addTextChangedListener {
                favoriteUserViewModel.search(binding.searchEditText.text.toString())
            }
        }
    }

    private fun initRecyclerView() {
        binding.favoriteUserRecyclerView.let { recyclerView ->
            recyclerView.adapter = RecyclerViewAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}
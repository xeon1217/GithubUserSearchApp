package com.example.githubusersearchapp.ui.detail

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.databinding.ActivityDetailUserBinding
import com.example.githubusersearchapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailUserBinding>() {
    private val detailUserViewModel: DetailViewModel by viewModel()

    override fun initToolbar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.apply {
            setTitle(R.string.detail_str)
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
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_user
    }

    private fun initViewModel() {
        binding.viewModel = detailUserViewModel
        detailUserViewModel.getUser(intent.extras?.get("user").toString())

        detailUserViewModel.apply {
            message.observe(this@DetailActivity, Observer { msg ->
                showToast(msg)
            })
        }

        binding.apply {
            saveButton.setOnClickListener {
                detailUserViewModel.saveUser(
                    user = detailUserViewModel.user.value?.copy(isFavorite = true, memo = memoEditText.text.toString())
                )
            }
            memoEditText.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}
                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val memo = detailUserViewModel.user.value?.memo.toString()

                    s.toString().let { str ->
                        saveButton.visibility = if (memo.isEmpty() || memo != str) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                    }
                }
            })
        }
    }
}
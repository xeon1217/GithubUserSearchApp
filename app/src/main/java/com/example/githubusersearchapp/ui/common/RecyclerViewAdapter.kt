package com.example.githubusersearchapp.ui.search

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.data.repository.searchUser.model.UserView
import com.example.githubusersearchapp.data.repository.searchUser.model.ViewType
import com.example.githubusersearchapp.databinding.ItemUserBinding
import com.example.githubusersearchapp.databinding.ItemUserFavoriteBinding
import com.example.githubusersearchapp.ui.detail.DetailActivity
import com.example.githubusersearchapp.ui.favorite.FavoriteActivity
import com.example.githubusersearchapp.ui.favorite.FavoriteViewModel
import kotlinx.android.synthetic.main.item_user.view.*

class RecyclerViewAdapter : ListAdapter<UserView, RecyclerView.ViewHolder>(
    SearchUserListDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.NORMAL.ordinal -> {
                NormalViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_user,
                        parent,
                        false
                    )
                )
            }
            ViewType.DETAIL.ordinal -> {
                DetailViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_user_favorite,
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = getItem(position)

        when (view.viewType) {
            ViewType.NORMAL.ordinal -> {
                (holder as NormalViewHolder).bind(view)
            }
            ViewType.DETAIL.ordinal -> {
                (holder as DetailViewHolder).bind(view)
            }
        }
    }

    //잘못 입력하거나 없을 경우 오류 발생!
    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    inner class NormalViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(view: UserView) {
            binding.apply {
                this.user = view.data
                executePendingBindings()

                root.apply {
                    setOnClickListener {
                        context.startActivity(
                            Intent(
                                context,
                                DetailActivity::class.java
                            ).putExtra("user", view.data.login)
                        )
                    }
                    favoriteCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                        user?.isFavorite = isChecked
                        (context as SearchActivity).binding.viewModel?.favorite(view.data)
                    }
                }
            }
        }
    }

    inner class DetailViewHolder(private val binding: ItemUserFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(view: UserView) {
            binding.apply {
                user = view.data
                executePendingBindings()

                root.apply {
                    setOnClickListener {
                        context.startActivity(
                            Intent(
                                context,
                                DetailActivity::class.java
                            ).putExtra("user", view.data.login)
                        )
                    }
                    favoriteCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                        user?.isFavorite = isChecked
                        (context as FavoriteActivity).binding.viewModel?.favorite(view)
                    }
                }
            }
        }
    }
}

private class SearchUserListDiffCallback :
    DiffUtil.ItemCallback<UserView>() {
    override fun areItemsTheSame(
        oldItem: UserView,
        newItem: UserView
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserView,
        newItem: UserView
    ): Boolean {
        return oldItem == newItem
    }
}
package com.github.ui.view.fragments.subfragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.common.CommonListAdapter
import com.github.network.entities.Repository
import com.github.util.loadWithGlide
import com.github.util.toKilo
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter : CommonListAdapter<Repository>(R.layout.item_repo) {
    override fun onBindData(viewHolder: RecyclerView.ViewHolder, item: Repository) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(item.owner.avatar_url, item.owner.login.first())
            repoNameView.text = item.name
            descriptionView.text = item.description
            langView.text = item.language ?: "Unknown"
            starView.text = item.stargazers_count.toKilo()
            forkView.text = item.forks_count.toKilo()
        }
    }

    override fun onItemClicked(itemView: View, item: Repository) {
        //todo
    }
}
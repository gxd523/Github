package com.github.ui.main.repo.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.common.adapter.CommonListAdapter
import com.github.network.entities.Repository
import com.github.ui.detail.RepoDetailActivity
import com.github.util.kilo
import com.github.util.launchActivity
import com.github.util.loadWithGlide
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter : CommonListAdapter<Repository>(R.layout.item_repo) {
    override fun onBindData(viewHolder: RecyclerView.ViewHolder, item: Repository) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(item.owner.avatar_url, item.owner.login.first())
            repoNameView.text = item.name
            descriptionView.text = item.description
            langView.text = item.language ?: "Unknown"
            starView.text = item.stargazers_count.kilo
            forkView.text = item.forks_count.kilo
        }
    }

    override fun onItemClicked(itemView: View, item: Repository) {
        itemView.context.launchActivity<RepoDetailActivity>(
            Pair(0, 0)// 去掉默认pending动画，否则跟右滑退出效果冲突
        ) {
            putParcelable(RepoDetailActivity.ARG_REPO, item)
        }
    }
}
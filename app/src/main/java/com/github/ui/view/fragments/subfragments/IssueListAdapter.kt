package com.github.ui.view.fragments.subfragments

import android.view.View
import com.github.R
import com.github.network.entities.Issue
import com.github.common.CommonListAdapter
import com.github.common.CommonViewHolder
import com.github.util.githubTimeToDate
import com.github.util.htmlText
import com.github.util.view
import kotlinx.android.synthetic.main.item_issue.view.*
import org.jetbrains.anko.imageResource

open class IssueListAdapter : CommonListAdapter<Issue>(R.layout.item_issue) {
    override fun onItemClicked(itemView: View, item: Issue) {
        // todo
    }

    override fun onBindData(viewHolder: CommonViewHolder, item: Issue) {
        viewHolder.itemView.apply {
            iconView.imageResource = if (item.state == "open") R.drawable.ic_issue_open else R.drawable.ic_issue_closed
            titleView.text = item.title
            timeView.text = githubTimeToDate(item.created_at).view()
            bodyView.htmlText = item.body_html
            commentCount.text = item.comments.toString()
        }
    }
}
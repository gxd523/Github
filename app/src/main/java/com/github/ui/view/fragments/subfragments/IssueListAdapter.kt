package com.github.ui.view.fragments.subfragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.network.entities.Issue
import com.github.ui.view.common.CommonListAdapter
import com.github.util.githubTimeToDate
import com.github.util.htmlText
import com.github.util.view
import kotlinx.android.synthetic.main.item_issue.view.*
import org.jetbrains.anko.imageResource

/**
 * Created by benny on 7/9/17.
 */
open class IssueListAdapter : CommonListAdapter<Issue>(R.layout.item_issue) {
    override fun onItemClicked(itemView: View, issue: Issue) {
        // todo
    }

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, issue: Issue) {
        viewHolder.itemView.apply {
            iconView.imageResource = if (issue.state == "open") R.drawable.ic_issue_open else R.drawable.ic_issue_closed
            titleView.text = issue.title
            timeView.text = githubTimeToDate(issue.created_at).view()
            bodyView.htmlText = issue.body_html
            commentCount.text = issue.comments.toString()
        }
    }
}
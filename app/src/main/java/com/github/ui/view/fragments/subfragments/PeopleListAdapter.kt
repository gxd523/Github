package com.github.ui.view.fragments.subfragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.common.CommonListAdapter
import com.github.network.entities.User
import com.github.util.loadWithGlide
import kotlinx.android.synthetic.main.item_user.view.*

class PeopleListAdapter : CommonListAdapter<User>(R.layout.item_user) {
    override fun onItemClicked(itemView: View, item: User) {
        // todo
    }

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, item: User) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(item.avatar_url, item.login.first())
            nameView.text = item.login
        }
    }
}
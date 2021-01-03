package com.github.ui.view.fragments.subfragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.network.entities.User
import com.github.ui.view.common.CommonListAdapter
import com.github.util.loadWithGlide
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by benny on 7/9/17.
 */
class PeopleListAdapter : CommonListAdapter<User>(R.layout.item_user) {
    override fun onItemClicked(itemView: View, item: User) {
        // todo
    }

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, user: User) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
            nameView.text = user.login
        }
    }
}
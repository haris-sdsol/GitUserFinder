package com.seattle.gituserfinder.views.user_detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seattle.gituserfinder.R
import com.seattle.gituserfinder.model.UserInfo
import com.seattle.gituserfinder.utils.ImageLoader

class FollowersListAdapter(
    val context: Context,
    val followersList: ArrayList<UserInfo>
) : RecyclerView.Adapter<FollowersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_view_followers_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ImageLoader.loadCircularImage(followersList.get(position).avatarUrl!!, holder.profilePicture!!)
        holder.username?.text = followersList.get(position).login
    }


    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profilePicture: ImageView? = null
        var username: TextView? = null

        init {
            profilePicture = itemView.findViewById(R.id.user_profile_img)
            username = itemView.findViewById(R.id.name_tv)
        }
    }
}
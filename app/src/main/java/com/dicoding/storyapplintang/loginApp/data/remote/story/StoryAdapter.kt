package com.dicoding.storyapplintang.loginApp.data.remote.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapplintang.databinding.ItemPostBinding
import com.dicoding.storyapplintang.loginApp.view.Detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tbl_story = getItem(position)
        holder.bind(tbl_story)
    }

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tbl_story: ListStoryItem?) {
            tbl_story?.let {
                with(binding) {
                    tvName.text = tbl_story.name
                    tvDescription.text = tbl_story.description

                    Glide.with(itemView.context)
                        .load(tbl_story.photoUrl)
                        .into(ivStory)

                    cardStory.setOnClickListener {
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.NameStory,tbl_story.name)
                        intent.putExtra(DetailActivity.DescStory, tbl_story.description)
                        intent.putExtra(DetailActivity.ImageStory, tbl_story.photoUrl)

                        val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivStory, "image"),
                            Pair(tvName, "name"),
                            Pair(tvDescription, "description")
                        )

                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                    }
                }
            }
        }
    }
}

object DIFF_CALLBACK : DiffUtil.ItemCallback<ListStoryItem>() {
    override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
        return oldItem == newItem
    }

}

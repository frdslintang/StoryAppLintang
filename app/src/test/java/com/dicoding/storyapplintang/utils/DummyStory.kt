package com.dicoding.storyapplintang.utils

import com.dicoding.storyapplintang.loginApp.data.remote.story.ListStoryItem
import com.dicoding.storyapplintang.loginApp.data.remote.story.StoryResponse

object DummyStory {
    fun generateStory(): StoryResponse {
        val listStoryItem = ArrayList<ListStoryItem>()
        for (i in 1..10) {
            val story = ListStoryItem(
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1698639172239_Zf6n1vx6.jpg",
                createdAt = "2023-10-30T04:12:52.240Z",
                name = "Iwan",
                description = "Udin",
                lon = 114.8304798,
                id = "story-RjFf-D5Ri7cHj0Nu",
                lat = -3.4372689
            )
            listStoryItem.add(story)
        }

        return StoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = listStoryItem
        )
    }

    fun generateNullStory(): StoryResponse {
        val listStoryItem = ArrayList<ListStoryItem>()

        return StoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = listStoryItem
        )
    }
}
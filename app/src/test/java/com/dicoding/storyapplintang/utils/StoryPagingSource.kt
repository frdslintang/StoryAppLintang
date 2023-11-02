package com.dicoding.storyapplintang.utils

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.storyapplintang.loginApp.data.remote.story.ListStoryItem

class StoryPagingSource: PagingSource<Int, List<ListStoryItem>>() {
    companion object {
        fun snapShot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, List<ListStoryItem>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<ListStoryItem>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}
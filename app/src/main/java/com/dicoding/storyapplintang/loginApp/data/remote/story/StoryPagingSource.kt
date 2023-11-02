package com.dicoding.storyapplintang.loginApp.data.remote.story

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.storyapplintang.loginApp.data.remote.ApiService

class StoryPagingSource(
    private val apiService: ApiService,
    private val token: String
): PagingSource<Int, ListStoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val client = apiService.getAllStories(token, position, params.loadSize)
            val story = client.listStory

            LoadResult.Page(
                data = story,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (story.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
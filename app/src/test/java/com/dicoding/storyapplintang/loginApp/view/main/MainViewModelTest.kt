package com.dicoding.storyapplintang.loginApp.view.main

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyapplintang.loginApp.data.LoginPreference
import com.dicoding.storyapplintang.loginApp.data.remote.story.DIFF_CALLBACK
import com.dicoding.storyapplintang.loginApp.data.remote.story.ListStoryItem
import com.dicoding.storyapplintang.loginApp.repository.StoryRepository
import com.dicoding.storyapplintang.utils.DummyStory
import com.dicoding.storyapplintang.utils.MainDispatcherRule
import com.dicoding.storyapplintang.utils.StoryPagingSource
import com.dicoding.storyapplintang.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock private lateinit var storyRepository: StoryRepository
    @Mock private lateinit var loginPreference: LoginPreference
    private var mockedStatic: MockedStatic<Log>? = null
    private val dummyStoryResponse = DummyStory.generateStory()
    private val nullStory = DummyStory.generateNullStory()

    @Before
    fun init() {
        mockedStatic = Mockito.mockStatic(Log::class.java)
    }

    @After
    fun close() {
        mockedStatic?.close()
    }

    @Test
    fun `when getMyStory should not null and return stories`() = runTest {
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapShot(dummyStoryResponse.listStory)
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data
        Mockito.`when`(storyRepository.getMyStory("token")).thenReturn(expectedStory)

        val viewModel = MainViewModel(storyRepository, loginPreference)
        val story: PagingData<ListStoryItem> = viewModel.getStory("token").getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(story)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStoryResponse.listStory.size, differ.snapshot().size)
        assertEquals(dummyStoryResponse.listStory, differ.snapshot())
        assertEquals(dummyStoryResponse.listStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when getMyStory should empty and return null`() = runTest {
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapShot(nullStory.listStory)
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data
        Mockito.`when`(storyRepository.getMyStory("token")).thenReturn(expectedStory)

        val viewModel = MainViewModel(storyRepository, loginPreference)
        val story: PagingData<ListStoryItem> = viewModel.getStory("token").getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(story)
        assertNotNull(differ.snapshot())
        assertEquals(nullStory.listStory.size, differ.snapshot().size)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}
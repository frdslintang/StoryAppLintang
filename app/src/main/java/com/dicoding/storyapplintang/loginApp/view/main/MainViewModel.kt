package com.dicoding.storyapplintang.loginApp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapplintang.loginApp.data.LoginPreference
import com.dicoding.storyapplintang.loginApp.data.remote.story.ListStoryItem
import com.dicoding.storyapplintang.loginApp.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepository: StoryRepository,
    private val loginPreferences: LoginPreference
) : ViewModel() {

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> =
        storyRepository.getMyStory(token).cachedIn(viewModelScope)

    fun checkIfTokenAvailable(): LiveData<String> {
        return loginPreferences.getToken().asLiveData()
    }

    fun logout(): LiveData<Unit> {
        val resultLiveData = MutableLiveData<Unit>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginPreferences.deleteToken()
                resultLiveData.postValue(Unit)
            } catch (e: Exception) {
                // Tangani error jika terjadi
                resultLiveData.postValue(Unit) // Atau dapat diganti dengan Result.Error jika diperlukan
            }
        }

        return resultLiveData
    }
}
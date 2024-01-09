package com.umang.assignment

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.umang.assignment.data.remote.image.ImageRepository
import com.umang.assignment.data.local.User
import com.umang.assignment.data.local.UserDao
import com.umang.assignment.data.remote.article.ArticleRepository
import com.umang.assignment.data.remote.article.articledatatype.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val userDao: UserDao,
    imageRepository: ImageRepository,
    articleRepository: ArticleRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {


    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    val profileState = mutableStateOf<User?>(null)

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: MutableLiveData<String?> = _toastMessage


    init{
        checkLoginStatus()
        fetchUserProfile()
    }


    fun signUp(profile: User) {
        viewModelScope.launch(Dispatchers.IO) {

            val existingUser = userDao.getUserByEmail(profile.email)

            if (existingUser == null) {
                val user = User(
                    email = profile.email,
                    name = profile.name,
                    number = profile.number,
                    password = profile.password
                )

                userDao.insertUser(user)
                profileState.value = user
                _isLoggedIn.value = true
                saveLoginStatus(user.email, user.password)
            } else {
                _toastMessage.postValue("User is Already registered")
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user = userDao.getUser(email, password)
            if (user != null) {

                profileState.value = user
                _isLoggedIn.value = true
                saveLoginStatus(email, password)
            } else {
                _toastMessage.postValue("User is Not registered")
            }
        }
    }


    fun logout() {
        _isLoggedIn.value = false
        profileState.value = null
        clearLoginStatus()
    }

    private fun checkLoginStatus() {
        _isLoggedIn.value = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false)
    }


    private fun fetchUserProfile() {
        viewModelScope.launch {
            // Fetch user profile from Room database
            profileState.value = userDao.getUserByEmail(
                email = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
            )
        }
    }

    private fun saveLoginStatus(email: String, password: String) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_LOGIN_STATUS, true)
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    private fun clearLoginStatus() {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_LOGIN_STATUS, false)
            remove(KEY_EMAIL)
            remove(KEY_PASSWORD)
            apply()
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }


    val images = imageRepository.getImages().cachedIn(viewModelScope)
    val articles = articleRepository.getRandomArticles().cachedIn(viewModelScope)

}
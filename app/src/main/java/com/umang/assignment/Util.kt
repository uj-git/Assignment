package com.umang.assignment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.umang.assignment.presentation.navgraph.DestinationScreen

@Composable
fun CheckSignedIn(viewModel: MainViewModel, navController: NavController) {

    val alreadySingIn = remember {
        mutableStateOf(false)
    }

    val signIn = viewModel.isLoggedIn.value

    if (signIn && !alreadySingIn.value) {
        alreadySingIn.value = true
        navController.navigate(DestinationScreen.Home.route) {
            popUpTo(0)
        }
    }
}

fun navigateTo(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

const val BASE_URL_Image = "https://commons.wikimedia.org/"
const val BASE_URL_Article = "https://en.wikipedia.org"
const val KEY_LOGIN_STATUS = "login_status"
const val KEY_EMAIL = "email"
const val KEY_PASSWORD = "password"
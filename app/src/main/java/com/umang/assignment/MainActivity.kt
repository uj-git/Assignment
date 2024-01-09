package com.umang.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umang.assignment.data.remote.article.articledatatype.Page
import com.umang.assignment.presentation.screens.BookmarkScreen
import com.umang.assignment.presentation.screens.ProfileScreen
import com.umang.assignment.presentation.screens.HomeScreen
import com.umang.assignment.presentation.screens.LogInScreen
import com.umang.assignment.presentation.navgraph.DestinationScreen
import com.umang.assignment.presentation.screens.DetailsScreen
import com.umang.assignment.presentation.screens.SignUpScreen
import com.umang.assignment.ui.theme.AssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AssignmentTheme {
                AppNavigation()

            }
        }
    }

    @Composable
    fun AppNavigation(){

        val navController = rememberNavController()
        val viewModel = hiltViewModel<MainViewModel>()

        NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){

            composable(DestinationScreen.SignUp.route){
                SignUpScreen(navController = navController, mainViewModel = viewModel)
            }
            composable(DestinationScreen.LogIn.route){
                LogInScreen(navController = navController, mainViewModel = viewModel)
            }
            composable(DestinationScreen.Home.route){
                HomeScreen(navController = navController, mainViewModel = viewModel)
            }
            composable(DestinationScreen.Bookmark.route){
                BookmarkScreen(navController = navController, mainViewModel = viewModel)
            }
            composable(DestinationScreen.Profile.route){
                ProfileScreen(navController = navController, mainViewModel = viewModel)
            }

//            composable(DestinationScreen.Details.route){
//                val page = it.arguments?.getParcelable<Page>("page")
//                page?.let{
//                    DetailsScreen(navController = navController, page = it)
//                }
//            }

        }
    }
}


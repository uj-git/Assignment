package com.umang.assignment.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.umang.assignment.MainViewModel
import com.umang.assignment.presentation.appnavigator.BottomNavigationItem
import com.umang.assignment.presentation.appnavigator.BottomNavigator


@Composable
fun BookmarkScreen(navController: NavController, mainViewModel: MainViewModel){

    BottomNavigator(selectedItem = BottomNavigationItem.Bookmark, navController = navController)
}
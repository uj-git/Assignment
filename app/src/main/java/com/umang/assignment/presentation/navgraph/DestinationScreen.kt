package com.umang.assignment.presentation.navgraph

import com.umang.assignment.data.remote.article.articledatatype.Page

sealed class DestinationScreen(
    var route : String
) {

    object SignUp : DestinationScreen("signUpScreen")
    object LogIn : DestinationScreen("logInScreen")
    object Home : DestinationScreen("homeScreen")
    object Bookmark : DestinationScreen("bookmarkScreen")
    object Profile : DestinationScreen("profileScreen")
    object Details : DestinationScreen("detailsScreen/{page}"){
        fun createRoute(page:Page) = "singleChatScreen/$page"
    }

}
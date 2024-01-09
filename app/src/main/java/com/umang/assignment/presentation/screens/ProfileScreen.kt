package com.umang.assignment.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.umang.assignment.MainViewModel
import com.umang.assignment.R
import com.umang.assignment.navigateTo
import com.umang.assignment.presentation.appnavigator.BottomNavigationItem
import com.umang.assignment.presentation.appnavigator.BottomNavigator
import com.umang.assignment.presentation.navgraph.DestinationScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, mainViewModel: MainViewModel) {
    val profileState = mainViewModel.profileState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text("Profile") },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navigateTo(navController, DestinationScreen.Home.route)
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        ProfileImage()

        Spacer(modifier = Modifier.height(16.dp))


        if (profileState != null) {
            ProfileDetail("Name", profileState.name)
        }
        if (profileState != null) {
            ProfileDetail("Email", profileState.email)
        }
        if (profileState != null) {
            ProfileDetail("Number", profileState.number)
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = {

                mainViewModel.logout()
                navigateTo(navController, DestinationScreen.SignUp.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Logout")
        }

        BottomNavigator(selectedItem = BottomNavigationItem.Profile, navController = navController)
    }
    

}

@Composable
fun ProfileImage() {

    val painter = painterResource(id = R.drawable.articlelogo)
    val contentDescription = null // specify content description
    val modifier = Modifier
        .size(120.dp)
        .clip(CircleShape)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
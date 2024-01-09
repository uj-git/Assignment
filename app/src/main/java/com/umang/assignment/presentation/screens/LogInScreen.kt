package com.umang.assignment.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.umang.assignment.CheckSignedIn
import com.umang.assignment.MainViewModel
import com.umang.assignment.navigateTo
import com.umang.assignment.presentation.navgraph.DestinationScreen

@Composable
fun LogInScreen(navController: NavController, mainViewModel: MainViewModel){

    CheckSignedIn(viewModel = mainViewModel, navController = navController)
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var email = remember {
                mutableStateOf(TextFieldValue())
            }

            var password = remember {
                mutableStateOf(TextFieldValue())
            }

            Text(
                text = "LOG IN!!" ,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = email.value ,
                onValueChange = {
                    email.value = it
                },
                shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                label = { Text(text = "Email")},
                placeholder = { Text(text = "Enter Your Email")},
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                label = { Text(text = "Password")},
                placeholder = { Text(text = "Enter Your Password")},
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                    mainViewModel.login(email.value.text,password.value.text)
                },
                modifier = Modifier
                    .padding(8.dp)

            ) {
                Text(text = "LogIn")
            }

            mainViewModel.toastMessage.observeAsState().value?.let { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                mainViewModel.clearToastMessage()
            }
            
            Text(
                text = "New User / Go to SignUp..",
                color = Color.Black,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreen.SignUp.route)
                    }
            )

        }
    }
    
}
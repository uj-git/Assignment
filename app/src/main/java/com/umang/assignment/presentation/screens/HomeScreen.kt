package com.umang.assignment.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.umang.assignment.MainViewModel
import com.umang.assignment.R
import com.umang.assignment.data.remote.article.articledatatype.Page
import com.umang.assignment.data.remote.image.imagedatatype.ImageInfoData
import com.umang.assignment.presentation.appnavigator.BottomNavigationItem
import com.umang.assignment.presentation.appnavigator.BottomNavigator

@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) {

    val lazyPagingItems: LazyPagingItems<ImageInfoData> =
        mainViewModel.images.collectAsLazyPagingItems()
    val articleLazyPagingItems: LazyPagingItems<Page> =
        mainViewModel.articles.collectAsLazyPagingItems()


    Scaffold(
        bottomBar = { BottomNavigator(selectedItem = BottomNavigationItem.Home, navController = navController) },
        content =
        {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Image(
                    painter = painterResource(id = R.drawable.articlelogo),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(10.dp).padding(10.dp))
                LazyColumn {
                    items(lazyPagingItems.itemCount) { index ->
                        if (index >= 0 && index < lazyPagingItems.itemCount) {
                            val image = lazyPagingItems[index]

                            val article = if (index < articleLazyPagingItems.itemCount) {
                                articleLazyPagingItems[index]
                            } else {
                                null
                            }

                            if (image != null && article != null) {
                                CombinedItem(image = image, article = article)
                            }
                        }
                    }
                }
//                BottomNavigator(
//                    selectedItem = BottomNavigationItem.Home,
//                    navController = navController
//                )
            }
        }
    )
}

@Composable
fun CombinedItem(image: ImageInfoData, article: Page) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            AsyncImage(
                model = image.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = article.title,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.revisions.first().content,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


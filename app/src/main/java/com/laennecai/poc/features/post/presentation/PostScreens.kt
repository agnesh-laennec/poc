package com.laennecai.poc.features.post.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laennecai.poc.NestedRoutes // Adjusted import
import com.laennecai.poc.features.post.data.model.Post
import com.laennecai.poc.features.post.domain.Result
// import androidx.compose.ui.text.style.TextOverflow // Reverted: Remove if not essential for simplified version

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScaffold(viewModel: PostViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Posts") }, // Reverted: Removed FontWeight.Bold
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary, // Reverted: to primary
                )
            )
        }
    ) { innerPadding ->
        PostListScreen(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScaffold(post: Post?, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Detail") }, // Reverted: Removed FontWeight.Bold
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary, // Reverted: to primary
                )
            )
        }
    ) { innerPadding ->
        if (post != null) {
            PostDetailScreen(post = post, modifier = Modifier.padding(innerPadding))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Post not found or still loading...")
            }
        }
    }
}

@Composable
fun PostListScreen(
    viewModel: PostViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    when (val result = viewModel.postsResult) {
        is Result.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator() // Reverted: Removed custom color
            }
        }

        is Result.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp), // Reverted: to original padding
                verticalArrangement = Arrangement.spacedBy(12.dp) // Reverted: to original spacing
            ) {
                items(result.data.size) { index ->
                    PostItem(
                        post = result.data[index],
                        onClick = { navController.navigate(NestedRoutes.postDetail(result.data[index].id)) }
                    )
                }
            }
        }

        is Result.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${result.exception.message}",
                    color = MaterialTheme.colorScheme.error
                    // Reverted: Removed custom style (MaterialTheme.typography.bodyLarge)
                )
            }
        }
        // Kept removed redundant cases as that was a correct change
    }
}

@Composable
fun PostItem(post: Post, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Reverted: to 2.dp
        shape = MaterialTheme.shapes.medium
        // Reverted: Removed custom colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium, // Reverted
                fontWeight = FontWeight.SemiBold, // Reverted
                // Reverted: Removed custom color
                modifier = Modifier.padding(bottom = 4.dp) // Reverted
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium, // Reverted
                // Reverted: Removed custom color
                maxLines = 3,
                // Reverted: Removed overflow = TextOverflow.Ellipsis
                // Reverted: padding for body text (original had no specific bottom padding here)
            )
        }
    }
}

@Composable
fun PostDetailScreen(post: Post, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.headlineMedium, // Reverted
            fontWeight = FontWeight.Bold
            // Reverted: Removed custom color
            // Reverted: Modifier.padding(bottom = 8.dp) to original (none or default)
        )
        Spacer(modifier = Modifier.height(12.dp)) // Reverted
        Text(
            text = post.body,
            style = MaterialTheme.typography.bodyLarge
            // Reverted: Removed custom color
        )
    }
} 
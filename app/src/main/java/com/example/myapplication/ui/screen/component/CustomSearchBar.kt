package com.example.myapplication.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CustomSearchBar(search:String="",onSearch:(String)->Unit={}){
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    TextField(
        value = search,
        onValueChange = { it ->
            onSearch(it)
        },
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            focusedIndicatorColor = Color.Transparent,
            focusedPlaceholderColor =  MaterialTheme.colorScheme.onTertiary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
        },
        trailingIcon = {
            if (search.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear icon",
                    Modifier.clickable {
                        onSearch("")
                    })
            }
        },
        placeholder = { Text(text = "Search", fontSize = 16.sp) },
        shape = RoundedCornerShape(8.dp)
    )
    LaunchedEffect(Unit) {
        focusManager.clearFocus()
    }
}
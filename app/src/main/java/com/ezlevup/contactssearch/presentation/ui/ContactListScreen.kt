package com.ezlevup.contactssearch.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezlevup.contactssearch.presentation.ui.components.ContactItem
import com.ezlevup.contactssearch.presentation.ui.components.SearchBar
import com.ezlevup.contactssearch.presentation.viewmodel.ContactViewModel

/**
 * 연락처 리스트 메인 화면
 *
 * @param viewModel ContactViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(viewModel: ContactViewModel = viewModel()) {
    val context = LocalContext.current
    val filteredContacts by viewModel.filteredContacts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // 화면 진입 시 연락처 로드
    LaunchedEffect(Unit) { viewModel.loadContacts(context) }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("연락처 검색") },
                        colors =
                                TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor =
                                                MaterialTheme.colorScheme.onPrimaryContainer
                                )
                )
            }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // 검색바
            SearchBar(query = searchQuery, onQueryChange = { viewModel.onSearchQueryChange(it) })

            // 로딩 상태
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@Column
            }

            // 에러 상태
            error?.let { errorMessage ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                    )
                }
                return@Column
            }

            // 연락처 리스트
            if (filteredContacts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                            text =
                                    if (searchQuery.isEmpty()) {
                                        "연락처가 없습니다"
                                    } else {
                                        "검색 결과가 없습니다"
                                    },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = filteredContacts, key = { it.id }) { contact ->
                        ContactItem(
                                contact = contact,
                                onClick = {
                                    // TODO: 연락처 클릭 시 동작 (전화 걸기 등)
                                }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

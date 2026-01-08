package com.ezlevup.contactssearch.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.contactssearch.data.model.Contact
import com.ezlevup.contactssearch.data.repository.ContactRepository
import com.ezlevup.contactssearch.util.KoreanUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 연락처 리스트 화면의 ViewModel
 *
 * 연락처 데이터 로딩, 검색, 즐겨찾기 등의 비즈니스 로직을 처리합니다.
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    // 전체 시스템 연락처 리스트
    private val _systemContacts = MutableStateFlow<List<Contact>>(emptyList())

    // 즐겨찾기 ID 목록 (DB 관찰)
    val favoriteIds: StateFlow<List<String>> =
            repository
                    .getFavoriteIds()
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 검색어
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 최종 결과물: 즐겨찾기 정보가 결합된 연락처 리스트
    val contacts: StateFlow<List<Contact>> =
            combine(_systemContacts, favoriteIds) { systemList, favIds ->
                        systemList.map { contact ->
                            contact.copy(isFavorite = favIds.contains(contact.id.toString()))
                        }
                    }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 필터링된 결과물
    val filteredContacts: StateFlow<List<Contact>> =
            combine(contacts, _searchQuery) { allContacts, query ->
                        val trimmedQuery = query.trim()
                        if (trimmedQuery.isEmpty()) {
                            allContacts
                        } else {
                            allContacts.filter { contact ->
                                KoreanUtils.matchesChosung(contact.name, trimmedQuery) ||
                                        contact.phoneNumber.contains(trimmedQuery)
                            }
                        }
                    }
                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 로딩 상태 및 에러 메시지
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** 연락처 데이터를 로드합니다 */
    fun loadContacts(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val contactList = repository.getContacts(context)
                _systemContacts.value = contactList
            } catch (e: Exception) {
                _error.value = "연락처를 불러오는 중 오류가 발생했습니다: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /** 즐겨찾기 상태를 토글합니다 */
    fun toggleFavorite(contactId: Long) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(contactId)
            } catch (e: Exception) {
                _error.value = "즐겨찾기 업데이트 실패: ${e.message}"
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun clearError() {
        _error.value = null
    }
}

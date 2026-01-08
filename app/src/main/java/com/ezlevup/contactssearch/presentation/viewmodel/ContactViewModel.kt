package com.ezlevup.contactssearch.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.contactssearch.data.model.Contact
import com.ezlevup.contactssearch.data.repository.ContactRepository
import com.ezlevup.contactssearch.util.KoreanUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 연락처 리스트 화면의 ViewModel
 *
 * 연락처 데이터 로딩, 검색, 필터링 등의 비즈니스 로직을 처리합니다.
 */
class ContactViewModel : ViewModel() {

    private val repository = ContactRepository()

    // 전체 연락처 리스트
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    // 필터링된 연락처 리스트
    private val _filteredContacts = MutableStateFlow<List<Contact>>(emptyList())
    val filteredContacts: StateFlow<List<Contact>> = _filteredContacts.asStateFlow()

    // 검색어
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 로딩 상태
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 에러 메시지
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * 연락처 데이터를 로드합니다
     *
     * @param context Android Context
     */
    fun loadContacts(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Repository에서 연락처 가져오기
                val contactList = repository.getContacts(context)
                _contacts.value = contactList
                _filteredContacts.value = contactList

            } catch (e: Exception) {
                _error.value = "연락처를 불러오는 중 오류가 발생했습니다: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * 검색어를 변경하고 연락처를 필터링합니다
     *
     * @param query 검색어
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterContacts()
    }

    /**
     * 검색어에 따라 연락처를 필터링합니다
     *
     * 검색 방식:
     * 1. 검색어가 비어있으면 전체 리스트 표시
     * 2. 검색어가 초성이면 초성 매칭
     * 3. 검색어가 일반 텍스트면 이름 또는 전화번호에 포함 여부 확인
     */
    private fun filterContacts() {
        val query = _searchQuery.value.trim()

        _filteredContacts.value = if (query.isEmpty()) {
            // 검색어가 없으면 전체 리스트
            _contacts.value
        } else {
            // 검색어가 있으면 필터링
            _contacts.value.filter { contact ->
                // 이름으로 검색 (초성 또는 일반 텍스트)
                KoreanUtils.matchesChosung(contact.name, query) ||
                        // 전화번호로 검색
                        contact.phoneNumber.contains(query)
            }
        }
    }

    /**
     * 에러 메시지를 초기화합니다
     */
    fun clearError() {
        _error.value = null
    }
}

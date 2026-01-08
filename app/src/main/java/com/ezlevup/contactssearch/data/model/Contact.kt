package com.ezlevup.contactssearch.data.model

/**
 * 연락처 정보를 담는 데이터 클래스
 *
 * @property id 연락처 고유 ID
 * @property name 연락처 이름
 * @property phoneNumber 전화번호
 * @property photoUri 프로필 사진 URI (nullable)
 */
data class Contact(
        val id: Long,
        val name: String,
        val phoneNumber: String,
        val photoUri: String? = null,
        val isFavorite: Boolean = false
)

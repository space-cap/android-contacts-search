package com.ezlevup.contactssearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 즐겨찾기 연락처 Entity
 *
 * 시스템 연락처의 고유 ID를 저장하여 즐겨찾기 상태를 관리합니다.
 * @property contactId 시스템 연락처의 CONTACT_ID
 */
@Entity(tableName = "favorite_contacts")
data class FavoriteContact(@PrimaryKey val contactId: String)

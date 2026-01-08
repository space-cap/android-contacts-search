package com.ezlevup.contactssearch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ezlevup.contactssearch.data.model.FavoriteContact
import kotlinx.coroutines.flow.Flow

/** 즐겨찾기 연락처 데이터 접근 인터페이스 (DAO) */
@Dao
interface FavoriteDao {
    /** 모든 즐겨찾기 연락처 ID 목록 조회 */
    @Query("SELECT contactId FROM favorite_contacts") fun getAllFavoriteIds(): Flow<List<String>>

    /** 즐겨찾기 추가 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteContact)

    /** 즐겨찾기 삭제 */
    @Delete suspend fun deleteFavorite(favorite: FavoriteContact)

    /** 특정 연락처의 즐겨찾기 여부 확인 */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_contacts WHERE contactId = :contactId)")
    suspend fun isFavorite(contactId: String): Boolean
}

package com.ezlevup.contactssearch.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.ezlevup.contactssearch.data.local.FavoriteDao
import com.ezlevup.contactssearch.data.model.Contact
import com.ezlevup.contactssearch.data.model.FavoriteContact
import kotlinx.coroutines.flow.Flow

/**
 * 연락처 데이터를 읽어오는 Repository
 *
 * ContentProvider를 통해 기기의 연락처 정보를 가져오고, Room DB를 통해 즐겨찾기 정보를 관리합니다.
 */
class ContactRepository(private val favoriteDao: FavoriteDao) {

    /** 모든 즐겨찾기 연락처 ID 목록 조회 (Flow) */
    fun getFavoriteIds(): Flow<List<String>> = favoriteDao.getAllFavoriteIds()

    /** 특정 연락처의 즐겨찾기 상태를 토글합니다 */
    suspend fun toggleFavorite(contactId: Long) {
        val idStr = contactId.toString()
        if (favoriteDao.isFavorite(idStr)) {
            favoriteDao.deleteFavorite(FavoriteContact(idStr))
        } else {
            favoriteDao.insertFavorite(FavoriteContact(idStr))
        }
    }

    /**
     * 기기의 모든 연락처를 가져옵니다
     *
     * @param context Android Context
     * @return 연락처 리스트 (이름 순으로 정렬됨)
     */
    fun getContacts(context: Context): List<Contact> {
        val contacts = mutableMapOf<Long, Contact>()

        // ContentResolver를 통해 연락처 데이터 쿼리
        val cursor =
                context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        arrayOf(
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER,
                                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                        ),
                        null,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
                )

        cursor?.use {
            val idColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoColumn = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn) ?: ""
                val phoneNumber = it.getString(numberColumn) ?: ""
                val photoUri = it.getString(photoColumn)

                // 중복 제거: 같은 ID의 연락처가 여러 번호를 가질 수 있으므로
                // 첫 번째 번호만 저장
                if (!contacts.containsKey(id)) {
                    contacts[id] =
                            Contact(
                                    id = id,
                                    name = name,
                                    phoneNumber = phoneNumber,
                                    photoUri = photoUri
                            )
                }
            }
        }

        // 이름 순으로 정렬하여 반환
        return contacts.values.sortedBy { it.name }
    }
}

package com.ezlevup.contactssearch

import android.content.Context
import com.ezlevup.contactssearch.data.local.AppDatabase
import com.ezlevup.contactssearch.data.local.FavoriteDao
import com.ezlevup.contactssearch.data.repository.ContactRepository

/** 앱의 의존성 수동 주입을 위한 Container (심플한 DI 대용) */
class AppContainer(context: Context) {
    private val database: AppDatabase = AppDatabase.getDatabase(context)
    val favoriteDao: FavoriteDao = database.favoriteDao()
    val contactRepository: ContactRepository = ContactRepository(favoriteDao)
}

/** Application 클래스 */
class ContactsSearchApplication : android.app.Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

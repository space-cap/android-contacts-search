package com.ezlevup.contactssearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ezlevup.contactssearch.data.model.FavoriteContact

/** 앱 데이터베이스 클래스 */
@Database(entities = [FavoriteContact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE
                    ?: synchronized(this) {
                        val instance =
                                Room.databaseBuilder(
                                                context.applicationContext,
                                                AppDatabase::class.java,
                                                "contacts_search_db"
                                        )
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}

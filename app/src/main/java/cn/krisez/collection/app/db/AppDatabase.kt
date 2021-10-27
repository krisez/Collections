package cn.krisez.collection.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.krisez.collection.app.db.dao.CollectionDao
import cn.krisez.collection.app.entity.CollectionItem

@Database(
    entities = [CollectionItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): CollectionDao
}
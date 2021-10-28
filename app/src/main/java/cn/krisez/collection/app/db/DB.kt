package cn.krisez.collection.app.db

import androidx.room.Room
import cn.krisez.collection.app.App

object DB {
    private var db: AppDatabase? = null

    @get:Synchronized
    val getDB: AppDatabase?
        get() {
            if (db == null) {
                synchronized(DB::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            App.context,
                            AppDatabase::class.java,
                            "collection.db"
                        ).build()
                    }
                }
            }
            return db
        }
}
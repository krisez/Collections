package cn.krisez.collection.app.db.dao

import androidx.room.*
import cn.krisez.collection.app.entity.CollectionItem

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg items: CollectionItem)

    @Query("select * from collection")
    suspend fun queryAll(): List<CollectionItem>

    @Delete
    fun delete(vararg items: CollectionItem)

    @Update
    fun updateItems(vararg items: CollectionItem)
}
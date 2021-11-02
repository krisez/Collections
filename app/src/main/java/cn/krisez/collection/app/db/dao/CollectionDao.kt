package cn.krisez.collection.app.db.dao

import androidx.room.*
import cn.krisez.collection.app.entity.CollectionItem

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg items: CollectionItem)

    @Query("select * from collection order by updateTime desc")
    suspend fun queryAll(): List<CollectionItem>

    @Delete
    fun delete(vararg items: CollectionItem)

    @Query("select * from collection where link=:link")
    fun getItem(link: String?):CollectionItem?
}
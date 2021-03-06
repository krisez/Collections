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

    @Query("select * from collection where upper(link)=upper(:link)")
    fun getItem(link: String?): CollectionItem?

    @Query("select * from collection where link like '%'||:search||'%' or name like '%'||:search||'%'")
    suspend fun query(search: String): List<CollectionItem>
}
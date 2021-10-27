package cn.krisez.collection.app.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class CollectionItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var link: String? = null,
    @ColumnInfo
    var size: String? = null,
    @ColumnInfo
    var updateTime: String? = null,
)
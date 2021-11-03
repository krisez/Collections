package cn.krisez.collection.app.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alibaba.fastjson.JSON

@Entity(tableName = "collection", indices = [Index(value = ["link"], unique = true)])
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(link)
        parcel.writeString(size)
        parcel.writeString(updateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String = JSON.toJSONString(this)

    companion object CREATOR : Parcelable.Creator<CollectionItem> {
        override fun createFromParcel(parcel: Parcel): CollectionItem {
            return CollectionItem(parcel)
        }

        override fun newArray(size: Int): Array<CollectionItem?> {
            return arrayOfNulls(size)
        }
    }


}
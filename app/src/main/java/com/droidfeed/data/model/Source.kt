package com.droidfeed.data.model

import androidx.databinding.ObservableBoolean
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.droidfeed.data.db.AppDatabase
import com.droidfeed.ui.adapter.diff.Diffable

@Entity(tableName = AppDatabase.SOURCE_TABLE)
data class Source(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "is_user_source")
    val isUserSource: Boolean

) : Diffable {

    @ColumnInfo(name = "is_active")
    var isActive: Boolean = true
        set(value) {
            isEnabled.set(value)
            field = value
        }

    @Ignore
    val isEnabled = ObservableBoolean()

    @Ignore
    val isRemovable = ObservableBoolean(false)

    override fun isSame(item: Any) = id == (item as Source).id

}
package com.kathir.newsreader.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

/**
 * Created by KATHIR on 12-01-2022.
 */
@Entity(tableName = "Newsdb")
class Newsdb(@field:ColumnInfo(name = "desc") var desc: String) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "task")
    var task: String? = null

}
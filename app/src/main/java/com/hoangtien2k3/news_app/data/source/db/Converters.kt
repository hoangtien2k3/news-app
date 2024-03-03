package com.hoangtien2k3.news_app.data.source.db

import androidx.room.TypeConverter
import com.hoangtien2k3.news_app.data.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) :String {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name, name)
    }

}

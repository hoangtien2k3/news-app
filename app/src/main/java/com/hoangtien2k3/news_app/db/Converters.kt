package com.hoangtien2k3.news_app.db

import androidx.room.TypeConverter
import com.hoangtien2k3.news_app.models.Source

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

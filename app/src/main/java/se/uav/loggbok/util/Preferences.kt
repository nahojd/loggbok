package se.uav.loggbok.util

import android.content.SharedPreferences


inline fun <reified T: Any> SharedPreferences.get(name: String, default: T) : T {
    val strData = this.getString(name, null) ?: return default

    return gson.fromJson(strData)
}

fun <T> SharedPreferences.put(name: String, obj: T) =
    this.edit()
        .putString(name, gson.toJson(obj))
        .apply()

package com.ayush.noonacademy.utils

import android.content.SharedPreferences

interface SharedPreferencesUtil {
    fun getString(key: String, defValue: String): String
    fun setString(key: String, value: String)

    @Deprecated("Better to avoid nullable values")
    fun getNullableString(key: String, defValue: String?): String?

    fun getInt(key: String, defValue: Int): Int
    fun setInt(key: String, value: Int)

    fun getBoolean(key: String, value: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)

    fun getLong(key: String, defValue: Long): Long
    fun setLong(key: String, value: Long)

    fun getStringSet(key: String, value: Set<String>): Set<String>
    fun setStringSet(key: String, value: Set<String>)
    fun remove(key: String)
    fun clear()
    fun clearExcept(keys: Set<String>)

    fun contains(key: String): Boolean
}

class SharedPreferencesUtilImpl(private val shared: SharedPreferences) : SharedPreferencesUtil {

    override fun getBoolean(key: String, value: Boolean): Boolean =
        shared.getBoolean(key, value)

    override fun setBoolean(key: String, value: Boolean) {
        shared.edit().putBoolean(key, value).apply()
    }

    override fun getStringSet(key: String, value: Set<String>): Set<String> =
        shared.getStringSet(key, value) ?: emptySet()

    override fun setStringSet(key: String, value: Set<String>) {
        shared.edit().putStringSet(key, value).apply()
    }

    override fun remove(key: String) {
        shared.edit().remove(key).apply()
    }

    override fun getString(key: String, defValue: String): String
            = shared.getString(key, defValue) ?: defValue

    override fun setString(key: String, value: String) {
        shared.edit().putString(key, value).apply()
    }

    override fun getNullableString(key: String, defValue: String?)
            = shared.getString(key, defValue)

    override fun getInt(key: String, defValue: Int)
            = shared.getInt(key, defValue)

    override fun setInt(key: String, value: Int) {
        shared.edit().putInt(key, value).apply()
    }

    override fun getLong(key: String, defValue: Long): Long  = shared.getLong(key, defValue)

    override fun setLong(key: String, value: Long) {
        shared.edit().putLong(key, value).apply()
    }

    override fun clear() {
        shared.edit().clear().apply()
    }

    override fun clearExcept(keys: Set<String>) {
        val editor = shared.edit()
        shared.all
            .filter { !keys.contains(it.key) }
            .forEach { editor.remove(it.key) }
        editor.apply()
    }

    override fun contains(key: String): Boolean = shared.contains(key)
}
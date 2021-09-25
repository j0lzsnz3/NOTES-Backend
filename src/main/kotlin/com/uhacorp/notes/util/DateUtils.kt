package com.uhacorp.notes.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS")

    /**
     * Returns today's date as java.util.Date object
     *
     * @return today's date as java.util.Date object
     */
    fun today(): Date {
        return Date()
    }

    /**
     * Returns today's date as dd-MM-yyyy HH:mm:ss.SSS format
     *
     * @return today's date as dd-MM-yyyy HH:mm:ss.SSS format
     */
    fun todayStr(): String {
        return sdf.format(today())
    }

    /**
     * Returns the formatted String date for the passed java.util.Date object
     *
     * @param date
     * @return
     */
    fun formattedDate(date: Date?): String? {
        return if (date != null) sdf.format(date) else todayStr()
    }
}
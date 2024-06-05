package com.duyvv.android.util.app

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object FormatUtils {

    private const val PATTERN_DATE = "HH:mm dd/MM/yyyy"
    private const val PATTERN_TIME = "HH:mm"
    private const val REQUEST_PATTERN_DATE = "dd/MM/yyyy HH:mm:ss"

    fun convertDate(date: Date?): String {
        return DateFormat.format(PATTERN_DATE, date).toString()
    }

    fun convertCalendarToString(date: Calendar): String {
        return DateFormat.format(REQUEST_PATTERN_DATE, date).toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringToDate(date: String?): Date? {
        return try {
            if (date != null) {
                SimpleDateFormat(REQUEST_PATTERN_DATE).parse(date)
            } else {
                null
            }
        } catch (e: ParseException) {
            null
        }
    }

    fun convertStringToCalendar(date: String): Calendar {
        val calendar = Calendar.getInstance()
        val d = convertStringToDate(date)
        if (d != null) {
            calendar.time = d
        }
        return calendar
    }

    fun getDayOfWeekFromCalendar(date: Calendar): String {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val dayOfWeekIndex = date.get(Calendar.DAY_OF_WEEK) - 1
        return daysOfWeek[dayOfWeekIndex]
    }

    fun getDayFromCalendar(date: Calendar): String {
        return date.get(Calendar.DAY_OF_MONTH).let {
            if (it < 10) "0$it" else "$it"
        }
    }

    fun getMonthFromCalendar(date: Calendar): String {
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        return months[date.get(Calendar.MONTH)]
    }

    fun convertDateToHourAndMinute(date: Calendar): String {
        return DateFormat.format("HH:mm", date).toString()
    }

    enum class DayOfWeek(val des: String) {
        MON("Mon"), TUE("Tue"),
    }
}

package tech.danielwaiguru.notebook.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateUtils {
    @SuppressLint("NewApi")
    fun formatDate(): String {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        return date.format(formatter)
    }
}
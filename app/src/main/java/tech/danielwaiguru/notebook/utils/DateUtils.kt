package tech.danielwaiguru.notebook.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate(): String {
        val date = Date(System.currentTimeMillis())
        val formatter = SimpleDateFormat("d MMM, yyyy HH:mm a", Locale.getDefault())
        return formatter.format(date)
    }
}
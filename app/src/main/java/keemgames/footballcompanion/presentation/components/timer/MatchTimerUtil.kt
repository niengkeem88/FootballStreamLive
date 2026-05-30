package keemgames.footballcompanion.presentation.components.timer

import keemgames.footballcompanion.domain.model.Match
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class MatchTimeDisplay(
    val primaryText: String,
    val secondaryText: String = "",
    val isLive: Boolean = false,
    val progressFraction: Float = 0f
)

fun computeMatchTimeDisplay(match: Match): MatchTimeDisplay {
    val status = match.status.uppercase()

    // Completed states
    if (status in listOf("FT", "AET", "ABD", "CAN", "WO", "AWARDED")) {
        return MatchTimeDisplay(primaryText = "Full Time", isLive = false, progressFraction = 1f)
    }

    if (status == "POSTPONED") {
        return MatchTimeDisplay(primaryText = "Postponed", isLive = false)
    }

    if (status == "NS") {
        val matchEpoch = parseMatchEpoch(match)
        if (matchEpoch != null) {
            val now = System.currentTimeMillis()
            val diffMillis = matchEpoch - now
            if (diffMillis > 0) {
                val totalSeconds = diffMillis / 1000
                val days = totalSeconds / 86400
                val hours = (totalSeconds % 86400) / 3600
                val minutes = (totalSeconds % 3600) / 60
                val secs = totalSeconds % 60

                val primary = when {
                    days > 0 -> "${days}d ${hours}h ${minutes}m"
                    hours > 0 -> "${hours}h ${minutes}m ${secs}s"
                    else -> "${minutes}m ${secs}s"
                }
                return MatchTimeDisplay(primaryText = primary, secondaryText = "Until kick-off", isLive = false)
            } else {
                return MatchTimeDisplay(primaryText = "Starting soon", isLive = false)
            }
        }
        return MatchTimeDisplay(primaryText = "Not Started", isLive = false)
    }

    // Live states
    if (status in listOf("1H", "2H", "HT", "ET", "P", "LIVE", "INT", "SUSP")) {
        val matchEpoch = parseMatchEpoch(match)
        if (matchEpoch != null) {
            val now = System.currentTimeMillis()
            val elapsed = now - matchEpoch
            if (elapsed > 0) {
                val elapsedMinutes = (elapsed / 60000).toInt()

                when (status) {
                    "HT" -> return MatchTimeDisplay(
                        primaryText = "Half Time", secondaryText = "${elapsedMinutes}m elapsed",
                        isLive = true, progressFraction = 0.5f
                    )
                    "1H" -> {
                        val mins = elapsedMinutes.coerceAtMost(45)
                        return MatchTimeDisplay(
                            primaryText = "${mins}'", secondaryText = "1st Half",
                            isLive = true, progressFraction = mins.toFloat() / 45f
                        )
                    }
                    "2H" -> {
                        val mins = (elapsedMinutes - 15).coerceIn(0, 45)
                        return MatchTimeDisplay(
                            primaryText = "${45 + mins}'", secondaryText = "2nd Half",
                            isLive = true, progressFraction = (45f + mins.toFloat()) / 90f
                        )
                    }
                    "ET" -> {
                        val mins = elapsedMinutes.coerceAtMost(30)
                        return MatchTimeDisplay(
                            primaryText = "${90 + mins}'", secondaryText = "Extra Time",
                            isLive = true, progressFraction = 0.85f + mins.toFloat() / 60f
                        )
                    }
                    "P" -> return MatchTimeDisplay(
                        primaryText = "Penalties", secondaryText = "${elapsedMinutes}m elapsed",
                        isLive = true, progressFraction = 1f
                    )
                    else -> return MatchTimeDisplay(
                        primaryText = "${elapsedMinutes}'", secondaryText = "Live",
                        isLive = true, progressFraction = 0.5f
                    )
                }
            }
        }
        return MatchTimeDisplay(primaryText = "Live", isLive = true)
    }

    return MatchTimeDisplay(primaryText = status, isLive = false)
}

private fun parseMatchEpoch(match: Match): Long? {
    // Build from date + matchTime
    val dateStr = match.date
    val timeStr = match.matchTime

    if (dateStr.isBlank()) return null

    try {
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE)

        val dateTime = if (timeStr.isNotBlank()) {
            // Try parsing time in various formats
            try {
                val time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"))
                LocalDateTime.of(date, time)
            } catch (_: Exception) {
                try {
                    val time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"))
                    LocalDateTime.of(date, time)
                } catch (_: Exception) {
                    date.atStartOfDay()
                }
            }
        } else {
            date.atStartOfDay()
        }

        return dateTime.toEpochSecond(ZoneOffset.UTC) * 1000
    } catch (_: Exception) {
        return null
    }
}

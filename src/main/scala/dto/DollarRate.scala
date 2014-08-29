package dto

import java.time.LocalDateTime

/**
 * Represents the data of dollar rate for a date.
 * @param date
 * @param value
 */
class DollarRate(var date: LocalDateTime, var value: Double)

package dto

import java.time.LocalDateTime

/**
 * Created by marcelosantana on 25/08/2014.
 */

/**
 * Represents the data of index rate from a date.
 * @param index
 * @param date
 * @param value
 */
class IndexRate(val index: String, val date: LocalDateTime, val value: Double)

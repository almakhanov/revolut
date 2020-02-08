package com.revolut.exrate.domain.entity

import java.math.BigDecimal
import java.math.RoundingMode

data class BaseRate(
    val base: String,
    val date: String,
    val rates: HashMap<String, Double>
) {
    fun mapToRateItems(baseRateValue: Double): MutableList<RateItem> {
        val list = mutableListOf<RateItem>()

        // Firstly base currency need to be added to list
        val baseRate = RateType.values().find { it.name == this.base }
        var tmpBaseValue: Double? = null
        if (baseRateValue != 0.0) {
            tmpBaseValue = baseRateValue
        }
        list.add(
            RateItem(
                code = this.base,
                label = baseRate?.label ?: "Unknown rate",
                icon = baseRate?.icon ?: 0,
                rate = tmpBaseValue,
                selectionIndex = tmpBaseValue.toString().indexOf('.')
            )
        )

        // Here mapping all other currencies
        rates.map { map ->
            val rate = RateType.values().find { it.name == map.key }
            var tmpValue: Double? = null
            if (baseRateValue != 0.0) {
                tmpValue = BigDecimal(map.value * baseRateValue).setScale(2, RoundingMode.HALF_EVEN)
                    .toDouble()
            }
            list.add(
                RateItem(
                    code = map.key,
                    label = rate?.label ?: "Unknown rate",
                    icon = rate?.icon ?: 0,
                    rate = tmpValue
                )
            )
        }

        // Making editable only first item as it's main responding currency
        list.getOrNull(0)?.editable = true
        return list
    }
}

package com.revolut.exrate.presentation.main

import androidx.lifecycle.MutableLiveData
import com.revolut.exrate.domain.entity.RateItem
import com.revolut.exrate.domain.entity.RateType
import com.revolut.exrate.domain.repository.RateRepository
import com.revolut.exrate.presentation.base.BaseViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class MainViewModel(private val repository: RateRepository) : BaseViewModel() {

    private var baseRateCode = RateType.EUR.name
    val rateList = MutableLiveData<MutableList<RateItem>>()
    private var baseRateValue = 1.0

    fun getRate() {
        makeRequest({ repository.getRate(baseRateCode) }) { res ->
            unwrap(res) {
                rateList.value = it.mapToRateItems(baseRateValue)
            }
        }
    }

    fun setBaseItem(item: RateItem) {
        baseRateValue = item.rate ?: 0.0
        baseRateCode = item.code
    }

    fun onItemValueChanged(item: RateItem, text: String) {
        if (text.startsWith('.')) return
        val tmp = if (text.isEmpty()) {
            0.0
        } else {
            text.toDouble()
        }
        if (item.code == rateList.value!![0].code)
            baseRateValue = BigDecimal(tmp).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}
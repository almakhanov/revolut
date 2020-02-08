package com.revolut.exrate.domain.entity

import com.revolut.exrate.R

enum class RateType(
    val label: String,
    val icon: Int
) {
    EUR("Euro", R.drawable.ic_eu),
    AUD("Australian Dollar", R.drawable.aud),
    BGN("Bulgarian Lev", R.drawable.bgn),
    BRL("Brazilian Real", R.drawable.brl),
    CAD("Canadian Dollar", R.drawable.cad),
    CHF("Swiss Franc", R.drawable.chf),
    CNY("Chinese Yuan Renminbi", R.drawable.cny),
    CZK("Czech Koruna", R.drawable.czk),
    DKK("Danish Krone", R.drawable.dkk),
    GBP("Pound Sterling", R.drawable.gb),
    HKD("Hong Kong Dollar", R.drawable.hkd),
    HRK("Croatian Kuna", R.drawable.hrk),
    HUF("Hungarian Forint", R.drawable.huf),
    IDR("Indonesian Rupiah", R.drawable.idr),
    ILS("Israeli new Shekel", R.drawable.ils),
    INR("Indian Rupee", R.drawable.inr),
    ISK("Icelandic Króna", R.drawable.isk),
    JPY("Japanese Yen", R.drawable.jpy),
    KRW("South Korean Won", R.drawable.krw),
    MXN("Mexican Peso", R.drawable.mxn),
    MYR("Malaysian Ringgit", R.drawable.myr),
    NOK("Norwegian Kron", R.drawable.nok),
    NZD("New Zealand Dollar", R.drawable.nzd),
    PHP("Philippine Peso", R.drawable.php),
    PLN("Polish Złoty", R.drawable.pln),
    RON("Romanian Leu", R.drawable.ron),
    RUB("Russian Ruble", R.drawable.rub),
    SEK("Swedish Krona", R.drawable.sek),
    SGD("Singapore Dollar", R.drawable.sgd),
    THB("Thai Baht", R.drawable.thb),
    TRY("Turkish Lira", R.drawable.trl),
    USD("US Dollar", R.drawable.us),
    ZAR("South African Rand", R.drawable.zar),
}
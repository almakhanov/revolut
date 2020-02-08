package com.revolut.exrate.presentation.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

inline fun EditText.doOnTextChange(crossinline body: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // do nothing
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                body(s.toString())
            }
        }
    })
}
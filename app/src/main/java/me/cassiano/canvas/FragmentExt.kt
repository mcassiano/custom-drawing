package me.cassiano.canvas

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

fun FragmentManager.runTransactionAsync(block: FragmentTransaction.() -> Unit) {
    with(beginTransaction()) {
        block()
        commit()
    }
}
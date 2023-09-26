package com.plcoding.cleanarchitecturenoteapp.feature.note.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

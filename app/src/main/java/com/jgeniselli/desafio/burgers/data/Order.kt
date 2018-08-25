package com.jgeniselli.desafio.burgers.data

import java.util.*

class Order(val burgerId: Int, val extraIds: List<Int>, val date: Date) {

    var burger: IBurger? = null

    companion object {
        fun valueOf(data: OrderData): Order = Order(data.id_sandwich, data.extras, Date(data.date))

        fun valuesOf(data: List<OrderData>) : List<Order> = data.map { valueOf(it) }
    }
}
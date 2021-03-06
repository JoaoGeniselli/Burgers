package com.jgeniselli.desafio.burgers.data.order

import java.io.Serializable

data class OrderData(
        val id: Int, val id_sandwich: Int, val extras: List<Int>, val date: Long
) : Serializable
package com.jgeniselli.desafio.burgers.data

import java.io.Serializable

data class OrderData(
        val id: Int, val id_sandwich: Int, val extras: List<Int>, val date: Long
) : Serializable

/*
[
    {
        "id": 1,
        "id_sandwich": "2",
        "extras": [
            1
        ],
        "date": 1535215427647
    },
    {
        "id": 2,
        "id_sandwich": "1",
        "extras": [],
        "date": 1535215467839
    },
    {
        "id": 3,
        "id_sandwich": "2",
        "extras": [],
        "date": 1535215490489
    },
    {
        "id": 4,
        "id_sandwich": "2",
        "extras": [
            4,
            4,
            4,
            4,
            4,
            4,
            4,
            4,
            4,
            4,
            2,
            2,
            2
        ],
        "date": 1535215523886
    }
]
 */
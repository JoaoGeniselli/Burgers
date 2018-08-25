package com.jgeniselli.desafio.burgers.commons

interface RequestBundle {

    class Req : RequestBundle

    companion object {
        fun empty() = Req()
    }

}


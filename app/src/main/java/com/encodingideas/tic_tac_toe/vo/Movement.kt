package com.encodingideas.tic_tac_toe.vo

import kotlinx.serialization.Serializable

@Serializable
data class Movement (
    val x: Int,
    val y: Int,
    val value: MovementValue
)
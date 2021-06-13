package com.avister.ml.models

data class Recognition(
    val label: Int,
    val confidence: Float,
    val timeCost: Long
)

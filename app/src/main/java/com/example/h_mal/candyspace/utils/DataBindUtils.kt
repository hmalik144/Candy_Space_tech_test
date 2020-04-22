package com.example.h_mal.candyspace.utils

import java.text.SimpleDateFormat
import java.util.*

object ConverterUtil {
    @JvmStatic fun epochToData(number: Int): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(number.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            "Unspecified"
        }
    }

}
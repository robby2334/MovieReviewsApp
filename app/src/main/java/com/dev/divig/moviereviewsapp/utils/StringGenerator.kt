package com.dev.divig.moviereviewsapp.utils

import java.security.SecureRandom

object StringGenerator {
    private const val ALGORITHM = "SHA1PRNG"
    private const val LETTERS = "abcdefghijklmnopqrstuvwxyz"
    private const val NUMBERS = "0123456789"
    private const val LENGTH = 24

    fun generateId(): String {
        var generateId = ""
        var idx = 0

        generateId += LETTERS
        generateId += NUMBERS

        val randomAlgorithm = SecureRandom.getInstance(ALGORITHM)
        val sb = StringBuilder(LENGTH)

        while (idx < LENGTH) {
            val randomInt: Int = randomAlgorithm.nextInt(generateId.length)
            sb.append(generateId[randomInt])
            idx++
        }

        return sb.toString()
    }
}
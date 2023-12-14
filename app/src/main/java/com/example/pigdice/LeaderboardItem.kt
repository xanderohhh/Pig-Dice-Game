package com.example.pigdice

class LeaderboardItem( var winnerName: String, var winnerTotalScore: Int, var formattedDate: String) {
    fun toCSV(): String {
        return "$winnerName,$winnerTotalScore,$formattedDate\n"
    }

    override fun toString(): String {
        return String.format("%-8s : %2d   %s", winnerName, winnerTotalScore, formattedDate)

    }
}
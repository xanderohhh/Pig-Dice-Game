package com.example.pigdice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

const val PIG_LEADER_BOARD = "pig_leader_board.txt"

class leaderboard : AppCompatActivity() {
    private lateinit var leaderBoardRecycler: RecyclerView
    private lateinit var btnPlayGame: Button
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    var leaderboardList = ArrayList<LeaderboardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        leaderBoardRecycler = findViewById(R.id.leaderBoardRecycler)
        leaderboardAdapter = LeaderboardAdapter(leaderboardList)
        btnPlayGame = findViewById(R.id.btnPlayGame)


        leaderBoardRecycler.layoutManager = LinearLayoutManager(applicationContext)
        leaderBoardRecycler.itemAnimator = DefaultItemAnimator()
        leaderBoardRecycler.adapter = leaderboardAdapter

        val extras = intent.extras

        if (extras != null) {
            val winnerName: String? = extras.getString("winnerName")
            val winnerTotalScore: Int? = extras.getInt("winnerTotalScore")
            val formattedDate = extras.getString("formattedDate")


            if (winnerName != "") {
                //Write data to a file
                val fileOutputStream: FileOutputStream =
                    openFileOutput(PIG_LEADER_BOARD, MODE_APPEND)
                val pigLeaderBoardFile = OutputStreamWriter(fileOutputStream)
                pigLeaderBoardFile.write("$winnerName,$winnerTotalScore,$formattedDate\n")
                pigLeaderBoardFile.close()
            }

        }

        readLeaderboardData()
    }

    fun showGameOnClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun readLeaderboardData() {
        //Read data from file and display it in the edittext window
        var displayString = ""
        val file = File(filesDir, PIG_LEADER_BOARD)
        if (file.exists()) {
            File(filesDir, PIG_LEADER_BOARD).forEachLine {
                val oneLeaderBoardLine = it.split(",")
                //displayString += "${oneLeaderBoardLine[0]}:\t\t${oneLeaderBoardLine[1]}\t\t${oneLeaderBoardLine[2]}\n"
                //Add a new item
                var leaderboardItem = LeaderboardItem(
                    oneLeaderBoardLine[0].toString(),
                    oneLeaderBoardLine[1].toInt(),
                    oneLeaderBoardLine[2].toString()
                )
                leaderboardList.add(leaderboardItem)
            }
        }
        // Reverse the list to display the newest entry first
        leaderboardList.reverse()

        // Notify the adapter that the dataset has changed
        leaderboardAdapter.notifyDataSetChanged()
    }

}
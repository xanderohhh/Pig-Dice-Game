package com.example.pigdice

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var tvUserGamesWonTotal: TextView
    private lateinit var tvUserTotalScore: TextView
    private lateinit var tvUserTurnTotal: TextView
    private lateinit var tvComputerName: TextView
    private lateinit var tvComputerGamesWonTotal: TextView
    private lateinit var tvComputerTotalScore: TextView
    private lateinit var tvComputerTurnTotal: TextView
    private lateinit var tvDiceTotal: TextView
    private lateinit var btnRoll: Button
    private lateinit var btnHold: Button
    private lateinit var diceLeft: ImageView
    private lateinit var diceRight: ImageView
    private lateinit var winnerImage: ImageView
    private lateinit var loseImage: ImageView

    private var userTurnTotal = 0
    private var userTotalScore = 0
    private var userGamesWon = 0
    private var computerTurnTotal = 0
    private var computerTotalScore = 0
    private var computerGamesWon = 0
    private var isUserTurn = true
    private var leftDieValue = 0
    private var rightDieValue = 0
    private var sum = 0
    private var winnerName: String = ""
    private var winnerTotalScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvUserName = findViewById(R.id.tvUserName)
        tvUserGamesWonTotal = findViewById(R.id.tvUserGamesWonTotal)
        tvUserTotalScore = findViewById(R.id.tvUserTotalScore)
        tvUserTurnTotal = findViewById(R.id.tvUserTurnTotal)
        tvComputerName = findViewById(R.id.tvComputerName)
        tvComputerGamesWonTotal = findViewById(R.id.tvComputerGamesWonTotal)
        tvComputerTotalScore = findViewById(R.id.tvComputerTotalScore)
        tvComputerTurnTotal = findViewById(R.id.tvComputerTurnTotal)
        tvDiceTotal = findViewById(R.id.tvDiceTotal)
        btnHold = findViewById(R.id.btnHold)
        btnRoll = findViewById(R.id.btnRoll)
        diceLeft = findViewById(R.id.diceLeft)
        diceRight = findViewById(R.id.diceRight)
        winnerImage = findViewById(R.id.winnerImage)
        loseImage = findViewById(R.id.loseImage)
        winnerImage.visibility = View.GONE
        loseImage.visibility = View.GONE
        if (savedInstanceState == null) {
            applyBackgroundColor()
        }
        if (userTurnTotal == 0) {
            btnHold.isEnabled = false
        }
    }

    override fun onSaveInstanceState(saveState: Bundle) {
        super.onSaveInstanceState(saveState)
        //saveState.putString("", .text.toString())
        saveState.putString("tvUserGamesWonTotal", tvUserGamesWonTotal.text.toString())
        saveState.putString("tvUserTotalScore", tvUserTotalScore.text.toString())
        saveState.putString("tvUserTurnTotal", tvUserTurnTotal.text.toString())
        saveState.putString("tvComputerGamesWonTotal", tvComputerGamesWonTotal.text.toString())
        saveState.putString("tvComputerTotalScore", tvComputerTotalScore.text.toString())
        saveState.putString("tvComputerTurnTotal", tvComputerTurnTotal.text.toString())
        saveState.putString("tvDiceTotal", tvDiceTotal.text.toString())
        saveState.putString("leftDieValue", leftDieValue.toString())
        saveState.putString("rightDieValue", rightDieValue.toString())
        saveState.putBoolean("isUserTurn", isUserTurn)

    }

    fun showLeaderBoardOnClick(v: View) {
        //Intent = structure used by the OS to start new activity
        showLeaderBoard()
    }

    fun showLeaderBoard() {
        val intent = Intent(this, leaderboard::class.java)
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val formattedDate = LocalDateTime.now().format(formatter)
        intent.putExtra("formattedDate", formattedDate)
        intent.putExtra("winnerName", winnerName)
        intent.putExtra("winnerTotalScore", winnerTotalScore)

        startActivity(intent)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tvUserGamesWonTotal.text = savedInstanceState.getString("tvUserGamesWonTotal", "0")
        tvUserTotalScore.text = savedInstanceState.getString("tvUserTotalScore", "0")
        tvUserTurnTotal.text = savedInstanceState.getString("tvUserTurnTotal", "0")
        tvComputerGamesWonTotal.text = savedInstanceState.getString("tvComputerGamesWonTotal", "0")
        tvComputerTotalScore.text = savedInstanceState.getString("tvComputerTotalScore", "0")
        tvComputerTurnTotal.text = savedInstanceState.getString("tvComputerTurnTotal", "0")
        tvDiceTotal.text = savedInstanceState.getString("tvDiceTotal", "0")
        isUserTurn = savedInstanceState.getBoolean("isUserTurn", true)
        leftDieValue = savedInstanceState.getString("leftDieValue", "0").toInt()
        rightDieValue = savedInstanceState.getString("rightDieValue", "0").toInt()
        sum = leftDieValue + rightDieValue
        userTurnTotal = tvUserTurnTotal.text.toString().toInt()
        userTotalScore = tvUserTotalScore.text.toString().toInt()
        userGamesWon = tvUserGamesWonTotal.text.toString().toInt()
        computerTurnTotal = tvComputerTurnTotal.text.toString().toInt()
        computerTotalScore = tvComputerTotalScore.text.toString().toInt()
        computerGamesWon = tvComputerGamesWonTotal.text.toString().toInt()
        applyBackgroundColor()
        setDiceImage(leftDieValue, rightDieValue)
        if (userTurnTotal == 0) {
            btnHold.isEnabled = false
        }

    }

    fun applyBackgroundColor() {
        if (isUserTurn) {
            tvUserName.setBackgroundColor(Color.CYAN)
            tvComputerName.setBackgroundColor(Color.WHITE)
        } else {
            tvUserName.setBackgroundColor(Color.WHITE)
            tvComputerName.setBackgroundColor(Color.CYAN)
        }
    }

    fun setUserTurn() {
        isUserTurn = !isUserTurn
    }

    private fun diceRoll() {
        leftDieValue = (20..50).random()
        rightDieValue = (20..50).random()
        setDiceImage(leftDieValue, rightDieValue)
    }

    private fun setDiceImage(leftDieValue: Int, rightDieValue: Int) {
        when (leftDieValue) {
            1 -> diceLeft.setImageResource(R.drawable.dice_1)
            2 -> diceLeft.setImageResource(R.drawable.dice_2)
            3 -> diceLeft.setImageResource(R.drawable.dice_3)
            4 -> diceLeft.setImageResource(R.drawable.dice_4)
            5 -> diceLeft.setImageResource(R.drawable.dice_5)
            6 -> diceLeft.setImageResource(R.drawable.dice_6)
        }

        when (rightDieValue) {
            1 -> diceRight.setImageResource(R.drawable.dice_1)
            2 -> diceRight.setImageResource(R.drawable.dice_2)
            3 -> diceRight.setImageResource(R.drawable.dice_3)
            4 -> diceRight.setImageResource(R.drawable.dice_4)
            5 -> diceRight.setImageResource(R.drawable.dice_5)
            6 -> diceRight.setImageResource(R.drawable.dice_6)
        }
    }

    fun btnRollOnClick(v: View) {
        winnerImage.visibility = View.GONE
        loseImage.visibility = View.GONE
        btnRoll.text = getString(R.string.roll)

        diceRoll()
        sum = leftDieValue + rightDieValue
        if (sum == 2) {
            sum = 0
            userTurnTotal = 0
            userTotalScore = 0
            tvDiceTotal.text = "$sum"
            tvUserTurnTotal.text = "$userTurnTotal"
            tvUserTotalScore.text = "$userTotalScore"
            setUserTurn()
            applyBackgroundColor()
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.notification))
                .setMessage(getString(R.string.you_rolled_snake_eyes_computers_turn))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    computerTurn()
                }.setCancelable(false).show()

        } else if (leftDieValue == 1 || rightDieValue == 1) {
            sum = 0
            userTurnTotal = 0
            tvDiceTotal.text = "$sum"
            tvUserTurnTotal.text = "$userTurnTotal"
            setUserTurn()
            applyBackgroundColor()
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.notification))
                .setMessage(getString(R.string.you_rolled_a_one_computers_turn))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    computerTurn()
                }.setCancelable(false).show()

        } else {
            userTurnTotal += sum
            tvDiceTotal.text = "$sum"
            tvUserTurnTotal.text = "$userTurnTotal"
        }
        btnHold.isEnabled = userTurnTotal != 0

    }

    fun btnHoldOnClick(v: View) {
        userTotalScore += userTurnTotal
        tvUserTotalScore.text = "$userTotalScore"
        userTurnTotal = 0
        tvUserTurnTotal.text = "$userTurnTotal"
        when (userTotalScore) {
            in 0..99 -> {
                setUserTurn()
                applyBackgroundColor()
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.notification))
                    .setMessage(getString(R.string.computers_turn))
                    .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                        computerTurn()
                    }.setCancelable(false).show()
            }


            in 100..999999999 -> {
                winnerName = tvUserName.text.toString()
                winnerTotalScore = tvUserTotalScore.text.toString().toInt()
                userTotalScore = 0
                computerTotalScore = 0
                tvUserTotalScore.text = "$userTotalScore"
                tvComputerTotalScore.text = "$computerTotalScore"
                userGamesWon++
                tvUserGamesWonTotal.text = "$userGamesWon"
                winnerImage.visibility = View.VISIBLE
                btnHold.isEnabled = false
                btnRoll.text = getString(R.string.start_over)
                isUserTurn = true
                showLeaderBoard()
            }

        }
    }

    private fun computerTurn() {
        btnRoll.isEnabled = false
        btnHold.isEnabled = false


        object : CountDownTimer(
            6000,
            2000
        ) {
            override fun onTick(millisUntilFinished: Long) {

                sum = leftDieValue + rightDieValue
                if (sum == 2) {
                    setUserTurn()
                    applyBackgroundColor()
                    sum = 0
                    computerTurnTotal = 0
                    computerTotalScore = 0
                    tvDiceTotal.text = "$sum"
                    tvComputerTurnTotal.text = "$computerTurnTotal"
                    tvComputerTotalScore.text = "$computerTotalScore"
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(getString(R.string.notification))
                        .setMessage(getString(R.string.computer_rolled_snake_eyes))
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            btnRoll.isEnabled = true
                            this.cancel()
                        }.setCancelable(false).show()

                } else if (leftDieValue == 1 || rightDieValue == 1) {
                    setUserTurn()
                    applyBackgroundColor()
                    sum = 0
                    computerTurnTotal = 0
                    tvDiceTotal.text = "$sum"
                    tvComputerTurnTotal.text = "$computerTurnTotal"
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(getString(R.string.notification))
                        .setMessage(getString(R.string.computer_rolled_a_one))
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            btnRoll.isEnabled = true
                            this.cancel()
                        }.setCancelable(false).show()

                } else {
                    computerTurnTotal += sum
                    tvDiceTotal.text = "$sum"
                    tvComputerTurnTotal.text = "$computerTurnTotal"
                }
            }
//            }

            override fun onFinish() {
                computerTotalScore += computerTurnTotal
                tvComputerTotalScore.text = "$computerTotalScore"
                computerTurnTotal = 0
                tvComputerTurnTotal.text = "$computerTurnTotal"
                if (computerTotalScore < 100) {
                    setUserTurn()
                    applyBackgroundColor()
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(getString(R.string.notification))
                        .setMessage(getString(R.string.your_turn))
                        .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            btnRoll.isEnabled = true
                        }.setCancelable(false).show()

                } else {
                    winnerName = tvComputerName.text.toString()
                    winnerTotalScore = tvComputerTotalScore.text.toString().toInt()
                    userTotalScore = 0
                    computerTotalScore = 0
                    tvComputerTotalScore.text = "$computerTotalScore"
                    tvUserTotalScore.text = "$userTotalScore"
                    computerGamesWon++
                    tvComputerGamesWonTotal.text = "$computerGamesWon"
                    loseImage.visibility = View.VISIBLE
                    btnRoll.text = getString(R.string.start_over)
                    btnRoll.isEnabled = true
                    setUserTurn()
                    applyBackgroundColor()
                    showLeaderBoard()
                }
            }
        }.start()
    }
}
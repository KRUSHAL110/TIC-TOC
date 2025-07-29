package com.example.tic_toc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameScreen(boardSize: Int, navController: NavController) {
    var board by remember { mutableStateOf(List(boardSize) { MutableList(boardSize) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }

    fun resetGame() {
        board = List(boardSize) { MutableList(boardSize) { "" } }
        currentPlayer = "X"
        winner = ""
    }

    fun checkWinner(): String {
        val lines = mutableListOf<List<String>>()

        // Rows & Columns
        for (i in 0 until boardSize) {
            lines.add(board[i])
            lines.add(List(boardSize) { board[it][i] })
        }

        // Diagonals
        lines.add(List(boardSize) { board[it][it] })
        lines.add(List(boardSize) { board[it][boardSize - it - 1] })

        for (line in lines) {
            if (line.all { it == "X" }) return "X"
            if (line.all { it == "O" }) return "O"
        }
        return ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$boardSize x $boardSize Tic Tac Toe",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        for (i in 0 until boardSize) {
            Row {
                for (j in 0 until boardSize) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(300.dp / boardSize)
                            .padding(2.dp)
                            .background(Color.LightGray, RoundedCornerShape(6.dp))
                            .clickable(enabled = board[i][j] == "" && winner == "") {
                                board = board.toMutableList().also {
                                    it[i] = it[i].toMutableList().also { row ->
                                        row[j] = currentPlayer
                                    }
                                }
                                winner = checkWinner()
                                if (winner == "") {
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            }
                    ) {
                        Text(
                            text = board[i][j],
                            fontSize = 28.sp,
                            color = if (board[i][j] == "X") Color.Blue else Color.Red
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            winner.isNotEmpty() -> Text("Winner: $winner", fontSize = 22.sp, color = Color.Green)
            board.all { row -> row.all { it.isNotEmpty() } } -> Text("It's a Draw!", fontSize = 22.sp)
            else -> Text("Turn: $currentPlayer", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { resetGame() }, modifier = Modifier.padding(8.dp)) {
                Text("Reset")
            }
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.padding(8.dp)) {
                Text("Back")
            }
        }
    }
}

package com.game.dabba.tictactoe;

/**
 * Created by dabba on 6/8/17.
 */

//A virtual representation of the Tic Tac Toe Game board
public class GameBoard {
    //Declare the global variables
    private String[][] board = new String[3][3];

    //Initialize the Game board filled with empty strings
    GameBoard() {
        //Initiate the game board with blanks
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    //Checks to make sure a mark doesn't already exist before placing the mark.
    public void placeMark(int xloc, int yloc, String mark) {
        if (board[xloc][yloc] == "")
            board[xloc][yloc] = mark;
    }

    //Determines if there is a winner or not checks each diagonal then loops through each row/column
    public boolean isWinner() {
        // Check Diagonals
        if (board[0][0] == board[1][1]
                && board[0][0] == board[2][2] && board[0][0] != "")
            return true;

        if (board[2][0] == board[1][1]
                && board[2][0] == board[0][2] && board[2][0] != "")
            return true;

        for (int i = 0; i < 3; i++) {
            // Check Rows
            if (board[i][0] == board[i][1]
                    && board[i][1] == board[i][2] && board[i][0] != "")
                return true;

            // Check Columns
            if (board[0][i] == board[1][i]
                    && board[1][i] == board[2][i] && board[0][i] != "")
                return true;
        }

        return false;
    }

    //Clears the game board by looping through each row and column and puts the empty string
    public void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    //getter that returns the virtual board
    public String[][] getBoard() {
        return board;
    }
}
package com.game.dabba.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class gamePlay extends AppCompatActivity {
    private boolean playFirst = true;
    private boolean selectX = true;

    private GameBoard board = null;
    private int moveCount = 0, xloc = 0, yloc = 0;
    private String mark = "X", aiMark = "O";
    private boolean isOver = false;
    private AI ai = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        fetchSettings();
        //Set up a new board and AI and assign the initial variables
        board = new GameBoard();
        ai = new AI(aiMark);

        if(!playFirst){
            clear();
            getAIMove(board);
        }
    }

    private void fetchSettings(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        playFirst = sharedPref.getBoolean(getString(R.string.setting_play_first), true);
        selectX = sharedPref.getBoolean(getString(R.string.setting_select_x), true);
        if (selectX){
            mark = "X";
            aiMark = "O";
        }
        else{
            mark = "O";
            aiMark = "X";
        }
    }

    public void resetClick(View v) {
        clear();
        if (!playFirst) getAIMove(board);
    }

    public void cellClick(View v) {
        //Get the id of the clicked object and assign it to a Textview variable
        TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        if (content == "" && !isOver) {
            //Find the X Y location values of the particular cell that was clicked
            switch (cell.getId()) {
                case R.id.cell11:
                    xloc = 0; yloc = 0; break;
                case R.id.cell12:
                    xloc = 0; yloc = 1; break;
                case R.id.cell13:
                    xloc = 0; yloc = 2; break;
                case R.id.cell21:
                    xloc = 1; yloc = 0; break;
                case R.id.cell22:
                    xloc = 1; yloc = 1; break;
                case R.id.cell23:
                    xloc = 1; yloc = 2; break;
                case R.id.cell31:
                    xloc = 2; yloc = 0; break;
                case R.id.cell32:
                    xloc = 2; yloc = 1; break;
                case R.id.cell33:
                    xloc = 2; yloc = 2; break;
            }

            //Place the player's mark on the specific X Y location on both the virtual and displayed board
            board.placeMark(xloc, yloc, mark);
            cell.setText(mark);

            //Increment move Count because a move was just made
            moveCount++;

            //Check to see if the game is over
            isOver = checkEnd(mark);

            //if the game game is over get the AI's move
            if (!isOver)
                getAIMove(board);

        }
    }

    private boolean checkEnd(String player) {
        //Checks the virtual board for a winner if there's a winner announce it with the provided player
        if (board.isWinner())
        {
            announce(true, player);
            return true;
        }
        //Check to see if we've reached our move total meaning it's a draw
        else if (moveCount >= 9)
        {
            announce(false, player);
            return true;
        }
        //If neither win or draw then the game is still on
        return false;
    }

    private void announce(boolean endState, String player) {
        //Check for if it's a win or a draw. if it's a win amend player with wins!
        //	if it's a lose replace player with it's a draw! I did this just because why
        //  declare another String when I can just reuse the one I have?
        if (endState == true)
            player = player + " wins!";
        else
            player = "It's a draw!";

        //Get the application Context and setup the Toast notification with the end state info
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();

        //Update statistics --> Karnaugh map solution to following truth table
        /*
            A       B           C         Y
        selectX player=="X"  endstate   result
        t	        t	        t	    w
        t	        t	        f	    l
        t	        f	        t	    l
        t	        f	        f	    w
        f	        t	        t	    l
        f	        t	        f	    w
        f	        f	        t	    w
        f	        f	        f	    l

        y = A'B'C + A'BC' + AB'C' + ABC
        */

        boolean win = ((!selectX && !player.equals("X") && endState) ||
                        (!selectX && player.equals("X") && !endState) ||
                        (selectX && !player.equals("X") && !endState) ||
                        (selectX && player.equals("X") && endState));



        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int win_score = sharedPref.getInt(getString(R.string.win_score), 0);
        int lost_score = sharedPref.getInt(getString(R.string.lost_score), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(win){
            editor.putInt(getString(R.string.win_score), win_score+1);
        }
        else{
            editor.putInt(getString(R.string.lost_score), lost_score+1);
        }
        editor.commit();
    }

    //Clears the game Board
    private void clear() {
        //Get the id list of all the Textview cells
        int[] idList = { R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21,
                R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33 };
        TextView cell;
        //For each cell clear the text with an empty string
        for (int item : idList) {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }
        //Reset the game state and clear the virtual board
        isOver = false;
        moveCount = 0;
        board.clear();
    }

    //Gets the AI's next move giving the current state of the board
    private void getAIMove(GameBoard board) {
        //Send the board to the AI for it to determine and return the move in an array {x,y}
        int[] move = ai.move(board,aiMark);
        TextView cell = null;
        //Determine the right cell to use by id first go to the right row then the right column
        switch (move[0]) {
            case 0:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell11);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell12);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell13);
                        break;
                }
                break;
            case 1:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell21);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell22);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell23);
                        break;
                }
                break;
            case 2:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell31);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell32);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell33);
                        break;
                }
                break;
        }

        //Make sure there's nothing already in the cell
        //	then place the mark with the ai's Mark, increment move count
        //	and check to see if the game's over
        if (cell != null && cell.getText() == "") {
            board.placeMark(move[0], move[1], aiMark);
            cell.setText(aiMark);
            moveCount++;
            isOver = checkEnd(aiMark);
        }
    }
}

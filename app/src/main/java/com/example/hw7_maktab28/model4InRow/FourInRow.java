package com.example.hw7_maktab28.model4InRow;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

public class FourInRow implements Serializable {





    private Player winner = null ;

    private TextView[][] textViews;
    private TextView turnTextView;
    private Player player1 ;
    private Player player2 ;
    private int level;

    private int countBlue , countRed;

    private Player defaultPlayer = new Player("Computer" , Color.BLACK);

    private Player turn ;
    Player[][] table ;
    int[] verticalLayoutIndex ;



    public FourInRow (TextView[][] textViews , LinearLayout[] verticalLayouts , TextView turnTextView , String player1Name , String player2Name)
    {
        player1 = new Player(player1Name , Color.BLUE );
        player2 =  new Player(player2Name , Color.RED);
        turn = player1;

        this.textViews = textViews;
        this.turnTextView = turnTextView;
        table = new Player[textViews.length][textViews.length];
        verticalLayoutIndex = new int[table.length];
        level = table.length;

        for(int i = 0 ; i<table.length ; i++)
        {
            verticalLayoutIndex[i] = table.length -1;
            for(int j = 0 ; j<table[i].length ; j++)
            {
                table[i][j] = defaultPlayer;
            }
        }
        drawLayout();
    }

    public void drawLayout()
    {
        for(int i = 0 ; i<textViews.length ; i++)
        {
            for(int j = 0 ; j<textViews[i].length ; j++)
            {
                textViews[i][j].setBackgroundColor(table[i][j].getColor());
            }
        }
        turnTextView.setTextColor(turn.getColor());
        turnTextView.setText("Turn : " + turn.getName());

    }

    public void switchTurn()
    {
        if(turn == player1)
        {
            turn = player2;
        }
        else
        {
            turn = player1;
        }
    }

    public void checkWin()
    {
        checkHorizontalRow();
        checkVerticalRow();
        checkDiagonalRow();
    }

    private void checkDiagonalRow() {

        for(int i = 0 ; i<level ; i++){
            countBlue = 0 ;
            countRed = 0;
            for(int j = 0 ; j+i<level; j++){
                checkCount(i+j , j);
            }
        }

        for(int i = 0 ; i<level ; i++){
            countBlue = 0 ;
            countRed = 0;
            for(int j = 0 ; j+i<level; j++){
                checkCount(j , i+j);
            }
        }


        for(int i = level-1 ; i>=0 ; i--){
            countBlue = 0 ;
            countRed = 0;
            for(int j = 0 ; i-j>=0; j++){
                checkCount(j , i-j);
            }
        }


        for(int i = 0 ; i<level ; i++){
            countBlue = 0 ;
            countRed = 0;
            for(int j = i ; j<level; j++){
                checkCount(j , level-j+i-1);
            }
        }
    }

    private void checkVerticalRow() {
        for(int i = 0 ; i<table.length ; i++) {
            countBlue = 0;
            countRed = 0;
            for (int j = 0; j < table[i].length; j++) {
                checkCount(j , i);
            }
        }
    }

    private void checkHorizontalRow() {

        for(int i = 0 ; i<table.length ; i++) {
            countBlue = 0;
            countRed =0;
              for (int j = 0; j < table[i].length; j++) {
                checkCount(i , j);
            }
        }
    }


    public boolean isGameFinished()
    {
        if(winner!=null) {
            return true;
        }
        return false;
    }

    public Player getWinner()
    {
        return winner;
    }

    public void savedInstance(TextView[][] textViews , LinearLayout[] verticalLayouts , TextView turnTextView )
    {
        this.textViews = textViews;
        this.turnTextView = turnTextView;
        drawLayout();
    }

    public void play (int i)
    {
        if(table[verticalLayoutIndex[i]][i] == defaultPlayer)
        {
            table[verticalLayoutIndex[i]][i] = turn;
            if(verticalLayoutIndex[i] != 0)
                verticalLayoutIndex[i]--;

            switchTurn();
            drawLayout();
            checkEqual();
            checkWin();
        }
    }


    private void checkEqual(){
        if(winner==null) {
            for (int i = 0; i < table.length; i++){
                for (int j = 0 ; j<table.length ; j++)
                    if(table[i][j]==defaultPlayer)
                        return;
            }
            winner = defaultPlayer;
        }
    }
    private void checkCount(int i , int j)
    {
        if (table[i][j] == player2)
            countRed++;
        else if (table[i][j] == player1)
            countBlue++;
        if (table[i][j] != player1)
            countBlue = 0;
        if (table[i][j] != player2)
            countRed = 0;
        if (countBlue == 4) {
            winner = player1;
            return;
        }
        if(countRed==4) {
            winner = player2;
            return;
        }
    }

}

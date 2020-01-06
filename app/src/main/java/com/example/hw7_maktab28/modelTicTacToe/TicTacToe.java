package com.example.hw7_maktab28.modelTicTacToe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hw7_maktab28.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class TicTacToe implements Serializable {

    Button[][] buttons;
    Status[][] table = new Status[3][3];
    TextView turnTextView;
    private Status turn = Status.O;
    private Score winner = null ;


    public TicTacToe (Button[][] buttons , TextView turnTextView )
    {
        this.buttons = buttons;
        this.turnTextView = turnTextView;

        for(int i = 0 ; i<table.length ; i++)
        {
            for(int j = 0 ; j<table[i].length ; j++)
            {
                table[i][j] = Status.E;
            }
        }

        drawlayout();
    }

    public void savedInstance(Button[][] buttons, TextView turnTextView)
    {
        this.buttons = buttons;
        this.turnTextView = turnTextView;
        drawlayout();
    }


    public boolean isGameFinished()
    {
        if(winner == null)
            return false;

        return true;
    }


    public void checkScore()
    {
       checkVertical();
       checkHorizontal();
       checkDiagonal();
       checkDraw();
    }

    public void checkHorizontal(){
        for(int i = 0 ; i<table.length ; i++)
        {
            if(table[i][0].equals(Status.X) && table[i][1].equals(Status.X)&& table[i][2].equals(Status.X)){
                winner = Score.X_WINS;
                return;
            }
            else if(table[i][0].equals(Status.O) && table[i][1].equals(Status.O)&& table[i][2].equals(Status.O)){
                winner = Score.O_WINS;
                return;
            }
        }
    }

    public void checkVertical()
    {
        for(int i = 0 ; i<table.length ; i++)
        {
            if(table[0][i].equals(Status.X) && table[1][i].equals(Status.X)&& table[2][i].equals(Status.X)){
                winner = Score.X_WINS;
                return;
            }
            else if(table[0][i].equals(Status.O) && table[1][i].equals(Status.O)&& table[2][i].equals(Status.O)){
                winner = Score.O_WINS;
                return;
            }
        }
    }

    public void checkDiagonal()
    {
        int countX = 0;
        int countO = 0;
        for(int i = 0 ; i<table.length ; i++)
        {
            if(table[i][i].equals(Status.X))
                countX++;
            else if(table[i][i].equals(Status.O))
                countO++;
        }
        if(countX == 3) {
            winner = Score.X_WINS;
            return;
        }
        else if(countO == 3) {
            winner = Score.O_WINS;
            return;
        }

        countO = 0 ;
        countX = 0;
        for (int i = 0 ; i<table.length ; i++)
        {
            if(table[i][table.length-i-1].equals(Status.X))
                countX++;
            else if(table[i][table.length-i-1].equals(Status.O))
                countO++;
        }
        if(countX == 3)
            winner=Score.X_WINS;
        else if(countO == 3)
            winner = Score.O_WINS;

    }

    public void checkDraw(){
        for(int i = 0 ; i<table.length ; i++)
        {
            for(int j = 0 ; j<table[i].length ; j++)
            {
                if(table[i][j].equals(Status.E))
                {
                    return;
                }
            }
        }
        winner = Score.DRAW;
    }


    public void drawlayout()
    {
        for(int i = 0 ; i<buttons.length ; i++)
        {
            for(int j = 0 ; j<buttons[i].length ; j++)
            {
                buttons[i][j].setText(table[i][j].toString());
            }
        }

        turnTextView.setText("Turn : " + turn.name());
    }


    public void switchTurn()
    {
        if(turn.equals(Status.X))
        {
            turn = Status.O;
        }
        else
        {
            turn = Status.X;
        }
    }



    public Score getWinner()
    {
        return winner;
    }

   public void play (int i,int j)
    {
        if(table[i][j].equals(Status.E))
        {
            table[i][j] = turn;
            checkScore();
            switchTurn();
            drawlayout();
        }
    }



}

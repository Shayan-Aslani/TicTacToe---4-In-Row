package com.example.hw7_maktab28;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hw7_maktab28.modelTicTacToe.TicTacToe;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicTacToeFragment extends Fragment {

    private Button[][] buttons;
    private TextView turnTextView ;
    private TicTacToe ticTacToe;
    private Button resetButton;


    public TicTacToeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ticTacToe" , (Serializable) ticTacToe);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
        initUi(view);
        // Inflate the layout for this fragment

        createTicTacToe(savedInstanceState);
        playTicTacToe(view);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTicTacToe(null);
                setTableStatus(true);
            }
        });

        return view;
    }

    private void createTicTacToe(Bundle savedInstanceState){
        if(savedInstanceState==null)
        {
            ticTacToe = new TicTacToe(buttons , turnTextView);
            resetButton.setVisibility(View.GONE);
        }
        else
        {
            ticTacToe = (TicTacToe) savedInstanceState.getSerializable("ticTacToe");
            ticTacToe.savedInstance(buttons ,turnTextView);
            if(ticTacToe.isGameFinished()) {
                setTableStatus(false);
                resetButton.setVisibility(View.VISIBLE);
            }
        }


    }




    private void initUi(View view) {
        buttons = new Button[][]{{view.findViewById(R.id.button0_0), view.findViewById(R.id.button0_1), view.findViewById(R.id.button0_2)},
                {view.findViewById(R.id.button1_0), view.findViewById(R.id.button1_1), view.findViewById(R.id.button1_2)},
                {view.findViewById(R.id.button2_0), view.findViewById(R.id.button2_1), view.findViewById(R.id.button2_2)}};
        turnTextView = view.findViewById(R.id.turnTextView);
        resetButton = view.findViewById(R.id.reset_Button);
    }

    private void playTicTacToe(View view) {

        for(int i = 0 ; i<buttons.length ; i++)
        {
            for(int j = 0 ; j<buttons[i].length ; j++)
            {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ticTacToe.play(finalI, finalJ);
                        if(ticTacToe.isGameFinished())
                            onFinishGame();
                    }
                });
            }
        }
    }

    private void onFinishGame()
    {
            setTableStatus(false);
            resetButton.setVisibility(View.VISIBLE);
            Snackbar.make(getView(), ticTacToe.getWinner().toString() + " Wins !!!" , Snackbar.LENGTH_SHORT).show();
    }

    private void setTableStatus(Boolean status)
    {
        for(int i = 0 ; i<buttons.length ; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setEnabled(status);
            }
        }
    }


}

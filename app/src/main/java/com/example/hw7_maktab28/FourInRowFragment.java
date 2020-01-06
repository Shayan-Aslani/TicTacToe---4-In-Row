package com.example.hw7_maktab28;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw7_maktab28.model4InRow.FourInRow;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourInRowFragment extends Fragment {

    public static final String FOUR_IN_ROW = "fourInRow";
    public static final String START_LAYOUT_VISIBILITY = "startLayoutVisibility";
    public static final String LEVEL = "level";

    private LinearLayout[] verticalLayouts;
    private FourInRow fourInRow;
    private TextView turnTextView;
    private Button startButton;
    private EditText player1EditText;
    private EditText player2EditText;
    private EditText levelEditText ;
    private TextView[][] textViews;
    private LinearLayout containerLayout;
    private LinearLayout startLayout;
    private int startLayoutVisibility = View.VISIBLE;
    private int level ;


    public FourInRowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FOUR_IN_ROW , fourInRow);
        outState.putInt(LEVEL, level);
        outState.putInt(START_LAYOUT_VISIBILITY, startLayout.getVisibility());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four_in_row, container, false);

        initUi(view);

        if(savedInstanceState!= null && savedInstanceState.getInt(START_LAYOUT_VISIBILITY) == View.GONE)
        {
            level = savedInstanceState.getInt(LEVEL);
            createGamePlay(view);
            fourInRow = (FourInRow) savedInstanceState.getSerializable(FOUR_IN_ROW);
            startLayout.setVisibility(savedInstanceState.getInt(START_LAYOUT_VISIBILITY));
            fourInRow.savedInstance(textViews ,verticalLayouts ,turnTextView);
            playFourInRow();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()) {
                    createGamePlay(view);
                    fourInRow = new FourInRow(textViews, verticalLayouts, turnTextView,
                            player1EditText.getText().toString() , player2EditText.getText().toString());
                    startLayout.setVisibility(View.GONE);
                    playFourInRow();
                }
                else if (level<5 || level>20)
                {
                    Toast.makeText(getActivity(), "Invalid Level !!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Invalid Player Name !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkInputs(){
       try {
           if ((level = Integer.parseInt(levelEditText.getText().toString())) < 5 || level > 20 ||
                   player1EditText.length() ==0  || player2EditText.length() == 0)
               return false;
       }
       catch (Exception e)
       {
           Toast.makeText(getActivity(), "Empty Level !!!", Toast.LENGTH_SHORT).show();
           return false;
       }
        return true;
    }

    public void initUi(View view){
       turnTextView = view.findViewById(R.id.turn_TextView);
       startButton = view.findViewById(R.id.start_Button);
       player1EditText = view.findViewById(R.id.player1_EditText);
       player2EditText = view.findViewById(R.id.player2_EditText);
       levelEditText = view.findViewById(R.id.level_EditText);
       containerLayout = view.findViewById(R.id.containerLayout);
       startLayout = view.findViewById(R.id.startLayout);
       startLayout.setVisibility(startLayoutVisibility);
    }


    public void createGamePlay(View view)
    {
        turnTextView.setVisibility(View.VISIBLE);
        containerLayout.setWeightSum(level);
        textViews = new TextView[level][level];
        verticalLayouts = new LinearLayout[level];

        for(int i = 0 ; i<level ; i++)
        {
            verticalLayouts[i] = new LinearLayout(getActivity());
            verticalLayouts[i].setOrientation(LinearLayout.VERTICAL);
            verticalLayouts[i].setWeightSum(level);
            LinearLayout.LayoutParams size = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            size.weight = 1;

            verticalLayouts[i].setLayoutParams(size);
            for(int j = 0 ; j<level ; j++)
            {
                size = new LinearLayout.LayoutParams
                        (80, 80);
                size.weight = 1;
                size.setMargins(8,8,8,8);

                textViews[j][i] = new TextView(getActivity());
                textViews[j][i].setText("");
                textViews[j][i].setBackgroundColor(Color.BLACK);
                textViews[j][i].setLayoutParams(size);
                verticalLayouts[i].addView(textViews[j][i]);
            }
            containerLayout.addView(verticalLayouts[i]);
        }
    }

    public void playFourInRow()
    {
         for (int i = 0; i < verticalLayouts.length; i++) {
                final int finalI = i;
                verticalLayouts[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fourInRow.play(finalI);
                        if(fourInRow.isGameFinished()) {
                            containerLayout.removeAllViews();
                            turnTextView.setVisibility(View.INVISIBLE);
                            startLayout.setVisibility(View.VISIBLE);
                            Snackbar.make(getView(), fourInRow.getWinner().getName() + " Wins !!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }
}

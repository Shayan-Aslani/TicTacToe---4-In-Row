package com.example.hw7_maktab28;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hw7_maktab28.modelTicTacToe.TicTacToe;

public class MainActivity extends AppCompatActivity {

    private Button ticTacToeBtn ;
    private Button fourInRowBtn;
    private FrameLayout mainFrameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        ticTacToeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.main_frame_layout);
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame_layout , new TicTacToeFragment())
                            .commit();
            }


        });

        fourInRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.main_frame_layout);
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame_layout , new FourInRowFragment())
                            .commit();
            }
        });
    }



    private void initUi()
    {
        ticTacToeBtn = findViewById(R.id.ticTacToe_Btn);
        fourInRowBtn = findViewById(R.id.fourInRow_Btn);
        mainFrameLayout = findViewById(R.id.main_frame_layout);
    }
}

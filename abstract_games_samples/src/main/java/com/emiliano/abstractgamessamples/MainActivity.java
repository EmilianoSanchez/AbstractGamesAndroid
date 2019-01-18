package com.emiliano.abstractgamessamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.emiliano.abstractgamessamples.tictactoe.TTTActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTicTacToe(View view) {
        Intent intent = new Intent(this, TTTActivity.class);
        startActivity(intent);
    }

}

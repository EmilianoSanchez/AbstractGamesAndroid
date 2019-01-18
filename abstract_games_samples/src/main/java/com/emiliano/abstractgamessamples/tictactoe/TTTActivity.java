package com.emiliano.abstractgamessamples.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.emiliano.abstractgameslib.ai.MinimaxStrategy;
import com.emiliano.abstractgameslib.core.Game;
import com.emiliano.abstractgameslib.core.GameImpl;
import com.emiliano.abstractgameslib.core.GameLogic;
import com.emiliano.abstractgameslib.uiUtils.AsyncPlayer;
import com.emiliano.abstractgamessamples.R;

public class TTTActivity extends AppCompatActivity {

    private TTTPlayerView boardView;
    private Game gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);
        boardView = (TTTPlayerView) findViewById(R.id.board);
        gameEngine = new GameImpl(new TTTGameLogic(), 2);
        gameEngine.setPlayer(0, boardView);
        //gameEngine.setPlayer(1,new RandomPlayer());
        gameEngine.setPlayer(1, new AsyncPlayer(
                        new MinimaxStrategy<TTTState, TTTAction, GameLogic<TTTState, TTTAction>>(2) {
                            @Override
                            protected double[] evaluateState(TTTState currentState) {
                                int positionState = currentState.getPosition(1, 1);
                                double[] result = new double[2];
                                if (positionState != -1) {
                                    result[positionState] = 0.5;
                                    result[(positionState + 1) % 2] = -0.5;
                                }
                                return result;
                            }
                        }
                )
        );

        boardView.setGameEngine(gameEngine);
        boardView.setMainActivity(this);

        gameEngine.initGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tictactoe, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(char c) {
        String msg = (c == 'T') ? "Game Ended. Tie" : "GameEnded. " + c + " win";

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameEngine.initGame();
        boardView.invalidate();
    }


}
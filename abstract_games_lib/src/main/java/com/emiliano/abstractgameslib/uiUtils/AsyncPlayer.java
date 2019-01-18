package com.emiliano.abstractgameslib.uiUtils;

import android.os.AsyncTask;

import com.emiliano.abstractgameslib.ai.Strategy;
import com.emiliano.abstractgameslib.core.Action;
import com.emiliano.abstractgameslib.core.Game;
import com.emiliano.abstractgameslib.core.GameLogic;
import com.emiliano.abstractgameslib.core.Player;
import com.emiliano.abstractgameslib.core.State;

import java.util.List;

public class AsyncPlayer<S extends State, A extends Action, G extends Game<S,A>> implements Player<S,A,G>{

    protected G game;
    private Strategy<S,A,GameLogic<S,A>> strategy;

    public AsyncPlayer(Strategy<S,A,GameLogic<S,A>> strategy){
        this.strategy = strategy;
    }

    @Override
    public void onGameInit(G game) {
        this.game = game;
        this.strategy.setGameLogic(game.getGameLogic());
    }

    @Override
    public void onSelectAction(S currentState,List<A> validActions) {
        //Log.i("TTT-RandomPlayer","onSelectAction:"+validActions);
        AsyncTaskPlay asyncTaskPlay = new AsyncTaskPlay(currentState,validActions);
        asyncTaskPlay.execute();
    }

    @Override
    public void onActionSuccessfullyExecuted() {

    }

    @Override
    public void onGameEnd() {

    }

    class AsyncTaskPlay extends AsyncTask<Object, Object, A> {

        S currentState;
        List<A> validActions;

        AsyncTaskPlay(S currentState,List<A> validActions){
            this.currentState=currentState;
            this.validActions=validActions;
        }

        @Override
        protected A doInBackground(Object ...objects) {
            A result=AsyncPlayer.this.strategy.selectAction(this.currentState,this.validActions);
            return result;
        }

        protected void onPostExecute(A result) {
           // Log.i("TTT-RandomPlayer","executeAction:"+result);
            AsyncPlayer.this.game.executeAction(result);
        }
    };
}

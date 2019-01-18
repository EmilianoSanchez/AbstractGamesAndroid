package com.emiliano.abstractgamessamples.tictactoe;

import com.emiliano.abstractgameslib.core.Action;

public class TTTAction implements Action {
    int x;
    int y;
    int currentPlayer;

    public TTTAction(int x, int y, int currentPlayer){
        this.x=x;
        this.y=y;
        this.currentPlayer=currentPlayer;
    }

    @Override
    public String toString() {
        return TTTGameLogic.intToCharMapping.get(currentPlayer)+"["+x+","+y+"]";
    }
}
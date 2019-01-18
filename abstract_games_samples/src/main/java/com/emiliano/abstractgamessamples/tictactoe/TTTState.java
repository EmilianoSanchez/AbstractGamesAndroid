package com.emiliano.abstractgamessamples.tictactoe;

import com.emiliano.abstractgameslib.core.State;

public class TTTState implements State {
    int turn;
    char[] elts;

    TTTState(){
        turn=0;
        elts=new char[9];
        for(int i=0;i<elts.length;i++)
            elts[i]=' ';
    }

    public TTTState(int turn, char[] elts) {
        this.turn=turn;
        this.elts=elts;
    }

    public char getElt(int x, int y) {
        return elts[3 * y + x];
    }

    @Override
    public int getTurn() {
        return turn;
    }

    @Override
    public State clone() {
        return new TTTState(this.turn,this.elts.clone());
    }

    void advanceTurn(){
        this.turn++;
        this.turn%=2;
    }

    public int getPosition(int x,int y){
        return TTTGameLogic.charToIntMapping.get(this.getElt(x,y));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int x=0;x<3;x++) {
            for (int y = 0; y < 3; y++)
                stringBuilder.append(getElt(x, y)).append('|');
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
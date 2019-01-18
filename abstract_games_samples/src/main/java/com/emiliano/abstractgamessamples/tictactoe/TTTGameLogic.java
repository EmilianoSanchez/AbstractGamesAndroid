package com.emiliano.abstractgamessamples.tictactoe;

import com.emiliano.abstractgameslib.core.GameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTTGameLogic implements GameLogic<TTTState,TTTAction>{

    public static Map<Integer,Character> intToCharMapping=new HashMap<Integer,Character>(){{
        put(0,'X');put(1,'O');put(-1,' ');
    }};
    public static Map<Character,Integer> charToIntMapping=new HashMap<Character,Integer>(){{
        put('X',0);put('O',1);put(' ',-1);
    }};

//    public TTTGameLogic() {
//        super(2);
//        setGameLogic(this);
//    }

    @Override
    public boolean applyAction(TTTState state, TTTAction action) {
        if (state.elts[3 * action.y + action.x] == ' ' && action.currentPlayer==state.getTurn()) {
            state.elts[3 * action.y + action.x] = intToCharMapping.get(action.currentPlayer);
            state.advanceTurn();
            return true;
        }
        return false;
    }

    @Override
    public boolean isEndState(TTTState state) {
        Pair<Boolean, Character> booleanCharacterPair = checkEnd(state);
        return booleanCharacterPair.first;
    }

    @Override
    public double[] getPlayerPayoffs(TTTState state) {
        Pair<Boolean, Character> booleanCharacterPair = checkEnd(state);
        if(booleanCharacterPair.second=='X')
            return new double[]{1.0,-1.0};
        else
        if(booleanCharacterPair.second=='O')
            return new double[]{-1.0,1.0};
        else
            return new double[]{0.0,0.0};
    }


//    @Override
//    public boolean executeAction(TicTacToeAction action) {
//        if (!ended  &&  currentState.elts[3 * action.y + action.x] == ' ') {
//            currentState.elts[3 * action.y + action.x] = charMapping.get(action.currentPlayer);
//            changeTurn();
//            checkEnd();
//            return true;
//        }
//        return false;
//    }

    @Override
    public TTTState getInitialState() {
        return new TTTState();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    public List<TTTAction> getValidActions(TTTState state) {
        int turn = state.getTurn();
        ArrayList actions=new ArrayList(9);
        for(int i=0;i<state.elts.length;i++)
            if(state.elts[i]==' ')
                actions.add(new TTTAction(i%3,i/3,turn));
        return actions;
    }


//    public boolean executeAction(int x, int y) {
//        return executeAction(new TTTAction(x,y,this.getCurrentTurn()));
//    }


    private static class Pair<F,S>{
        F first;
        S second;
        Pair(F first,S second){
            this.first=first;
            this.second=second;
        }
        static<F,S> Pair<F,S> create(F first,S second){
            return new Pair(first,second);
        }
    }

    private Pair<Boolean,Character> checkEnd(TTTState state) {
        for (int i = 0; i < 3; i++) {
            if (state.getElt(i, 0) != ' ' &&
                    state.getElt(i, 0) == state.getElt(i, 1)  &&
                    state.getElt(i, 1) == state.getElt(i, 2)) {
//                ended = true;
//                return state.getElt(i, 0);
                return Pair.create(true,state.getElt(i, 0));
            }

            if (state.getElt(0, i) != ' ' &&
                    state.getElt(0, i) == state.getElt(1, i)  &&
                    state.getElt(1, i) == state.getElt(2, i)) {
//                ended = true;
//                return state.getElt(0, i);
                return Pair.create(true,state.getElt(0, i));
            }
        }

        if (state.getElt(0, 0) != ' '  &&
                state.getElt(0, 0) == state.getElt(1, 1)  &&
                state.getElt(1, 1) == state.getElt(2, 2)) {
//            ended = true;
//            return state.getElt(0, 0);
            return Pair.create(true,state.getElt(0, 0));
        }

        if (state.getElt(2, 0) != ' '  &&
                state.getElt(2, 0) == state.getElt(1, 1)  &&
                state.getElt(1, 1) == state.getElt(0, 2)) {
//            ended = true;
//            return state.getElt(2, 0);
            return Pair.create(true,state.getElt(2, 0));
        }

        for (int i = 0; i < 9; i++) {
            if (state.elts[i] == ' ') {
//                return ' ';
                return Pair.create(false, ' ');
            }
        }

//        return 'T';
        return Pair.create(true,'T');
    }


}
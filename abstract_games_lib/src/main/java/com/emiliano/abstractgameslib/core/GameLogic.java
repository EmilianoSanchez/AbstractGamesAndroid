package com.emiliano.abstractgameslib.core;

import java.util.List;

public interface GameLogic<S extends State, A extends Action> {

    S getInitialState();

    int getNumPlayers();

    List<A> getValidActions(S state);

    boolean applyAction(S state, A action);

    boolean isEndState(S state);

    double[] getPlayerPayoffs(S state);

}

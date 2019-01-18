package com.emiliano.abstractgameslib.core;

public interface Game<S extends State, A extends Action> {

    GameLogic<S, A> getGameLogic();

    void setPlayer(int turn, Player<S,A,? extends GameLogic<S,A>> player);

    boolean isGameEnd();

    boolean isGamePlaying();

    S getCurrentState();

    int getCurrentTurn();

    double[] getPlayerPayoffs();

    //Game loop:

    boolean initGame();

    boolean executeAction(A action);

}

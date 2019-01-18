package com.emiliano.abstractgameslib.core;

import java.util.List;

public interface Player<S extends State, A extends Action, G extends Game<S, A>> {

    void onGameInit(G game);

    void onSelectAction(S currentState, List<A> validActions);

    void onActionSuccessfullyExecuted();

    void onGameEnd();

}

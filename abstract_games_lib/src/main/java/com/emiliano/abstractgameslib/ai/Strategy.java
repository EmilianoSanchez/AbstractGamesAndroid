package com.emiliano.abstractgameslib.ai;

import com.emiliano.abstractgameslib.core.*;

import java.util.List;

public interface Strategy<S extends State, A extends Action, G extends GameLogic<S, A>> {

    void setGameLogic(G gameLogic);

    A selectAction(S currentState, List<A> validActions);

}

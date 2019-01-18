package com.emiliano.abstractgameslib.ai;

import com.emiliano.abstractgameslib.core.*;

public abstract class StrategyImpl<S extends State, A extends Action, G extends GameLogic<S, A>> implements Strategy<S, A, G> {

    protected G gameLogic;

    @Override
    public void setGameLogic(G gameLogic) {
        this.gameLogic = gameLogic;
    }

}

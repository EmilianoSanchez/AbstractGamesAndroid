package com.emiliano.abstractgameslib.ai;

import com.emiliano.abstractgameslib.core.*;

import java.util.List;

public class FirstActionStrategy<S extends State, A extends Action, G extends GameLogic<S, A>> extends StrategyImpl<S, A, G> {

    @Override
    public A selectAction(S currentState, List<A> validActions) {
        return validActions.get(0);
    }

}

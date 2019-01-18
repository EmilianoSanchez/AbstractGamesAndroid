package com.emiliano.abstractgameslib.ai;

import com.emiliano.abstractgameslib.core.*;

import java.util.List;

public class MinimaxStrategy<S extends State, A extends Action, G extends GameLogic<S, A>> extends StrategyImpl<S, A, G> {

    /**
     * number of moves to look into the future
     */
    protected int maxDepth = 2;
    protected int currentTurn;

    public MinimaxStrategy(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public A selectAction(S currentState, List<A> validActions) {
        this.currentTurn = currentState.getTurn();

        double bestResult = Double.NEGATIVE_INFINITY;
        A bestAction=null;// = validActions.get(0);
        for (A action : validActions) {
            S auxState = (S) currentState.clone();
            this.gameLogic.applyAction(auxState, action);

            double evaluationResult = minValue(1, auxState);

            //System.out.println("MaxValue. Deep: 0. "+action);
            //System.out.println("evaluationResult: "+ evaluationResult);
            //System.out.println(auxState);

            if (evaluationResult > bestResult) {
                bestResult = evaluationResult;
                bestAction = action;
            }
        }

        //System.out.println("Deep: 0. Max Action: "+bestAction);
        return bestAction;
    }

    private double minValue(int depth, S currentState) {
        if (this.gameLogic.isEndState(currentState)) {
            double[] playerPayoffs = this.gameLogic.getPlayerPayoffs(currentState);
            double currentPlayerPayoff = playerPayoffs[currentTurn];
            if(currentPlayerPayoff<0.0){
                currentPlayerPayoff+=depth*0.1;
            }else{
                currentPlayerPayoff-=depth*0.1;
            }
            return currentPlayerPayoff;
        } else {
            if (depth == this.maxDepth) {
                double[] playerExpectedPayoffs = evaluateState(currentState);
                double currentPlayerEspectedPayoff = playerExpectedPayoffs[currentTurn];
                return currentPlayerEspectedPayoff;
            } else {
                List<A> actions = this.gameLogic.getValidActions(currentState);
                double currentMin = Double.MAX_VALUE;

                for (A currentAction : actions) {
                    S auxState = (S) currentState.clone();
                    this.gameLogic.applyAction(auxState, currentAction);

                    double score = 0.0;
                    if(auxState.getTurn()==currentTurn)
                        score=maxValue(depth + 1, auxState);
                    else
                        score=minValue(depth + 1, auxState);

                    //System.out.println("MinValue. Deep: "+depth+". "+currentAction);
                    //System.out.println("score: "+ score);
                    //System.out.println(auxState);

                    if (score < currentMin) {
                        currentMin = score;
                    }
                }

                //System.out.println("Deep: "+depth+". currentMin: "+currentMin);
                return currentMin;
            }
        }
    }

    private double maxValue(int depth, S currentState) {
        if (this.gameLogic.isEndState(currentState)) {
            double[] playerPayoffs = this.gameLogic.getPlayerPayoffs(currentState);
            double currentPlayerPayoff = playerPayoffs[currentTurn];
            if(currentPlayerPayoff<0.0){
                currentPlayerPayoff+=depth*0.1;
            }else{
                currentPlayerPayoff-=depth*0.1;
            }
            return currentPlayerPayoff;
        } else {
            if (depth == this.maxDepth) {
                double[] playerExpectedPayoffs = evaluateState(currentState);
                double currentPlayerEspectedPayoff = playerExpectedPayoffs[currentTurn];
                return currentPlayerEspectedPayoff;
            } else {
                List<A> actions = this.gameLogic.getValidActions(currentState);
                double currentMax = Double.MIN_VALUE;

                for (A currentAction : actions) {
                    S auxState = (S) currentState.clone();
                    this.gameLogic.applyAction(auxState, currentAction);
                    double score = minValue(depth + 1, auxState);

                    //System.out.println("MaxValue. Deep: "+depth+". "+currentAction);
                    //System.out.println("score: "+ score);
                    //System.out.println(auxState);

                    if (score > currentMax) {
                        currentMax = score;
                    }
                }

//                System.out.println("Deep: "+depth+". currentMax: "+currentMax);
                return currentMax;
            }
        }
    }

    protected double[] evaluateState(S currentState){
        return new double[this.gameLogic.getNumPlayers()];
    }

//    protected double evaluateState(S currentState) {
//        int numPlayers = this.gameLogic.getNumPlayers();
//        int result = 0;
//        for (int i = 0; i < numPlayers; i++) {
//            if (i == this.currentTurn)
//                result += getPlayerPayoffEstimation(currentState, i);
//            else
//                result -= getPlayerPayoffEstimation(currentState, i);
//        }
//        return result;
//    }
//
//    protected abstract double getPlayerPayoffEstimation(S state, int idPlayer);
}

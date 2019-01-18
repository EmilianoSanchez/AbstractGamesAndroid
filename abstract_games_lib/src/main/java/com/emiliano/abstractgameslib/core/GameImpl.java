package com.emiliano.abstractgameslib.core;

public class GameImpl<S extends State, A extends Action> implements Game<S, A> {

    public static final int WAITING_STATUS = 0;
    public static final int PLAYING_STATUS = 1;
    public static final int END_STATUS = 2;

    private GameLogic<S, A> gameLogic;
    private int gameStatus;
    private Player<S, A, GameImpl<S, A>>[] players;
    protected S currentState;

    public GameImpl(int numPlayers) {
        this(null, numPlayers);
    }

    public GameImpl(GameLogic<S, A> gameLogic) {
        this(gameLogic,gameLogic.getNumPlayers());
    }

    public GameImpl(GameLogic<S, A> gameLogic, int numPlayers) {
        this.gameLogic = gameLogic;
        this.gameStatus = WAITING_STATUS;
        this.players = new Player[numPlayers];
    }

    public void setGameLogic(GameLogic<S, A> gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public GameLogic<S, A> getGameLogic() {
        return gameLogic;
    }

    public void setPlayer(int turn, Player player) {
        if (turn < 0 || turn >= players.length)
            throw new IllegalArgumentException("Invalid turn: " + turn);
        this.players[turn] = player;
    }

    public int getNumPlayers() {
        return players.length;
    }

    public boolean isPlayerMissed() {
        for (Player<S, A, GameImpl<S, A>> player : players)
            if (player == null)
                return true;
        return false;
    }

    public int getCurrentTurn() {
        return currentState.getTurn();
    }

    public S getCurrentState() {
        return this.currentState;
    }

    public double[] getPlayerPayoffs() {
        return this.gameLogic.getPlayerPayoffs(currentState);
    }

    public boolean isGameEnd() {
        return GameImpl.END_STATUS == this.gameStatus;
    }

    public boolean isGamePlaying() {
        return GameImpl.PLAYING_STATUS == this.gameStatus;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public boolean initGame() {
        if (!this.isPlayerMissed()) {
            gameStatus = PLAYING_STATUS;
            currentState = this.gameLogic.getInitialState();
            for (Player<S, A, GameImpl<S, A>> player : players)
                player.onGameInit(this);
            this.players[getCurrentTurn()].onSelectAction(currentState, this.gameLogic.getValidActions(currentState));
        }
        return false;
    }

    public boolean executeAction(A action) {
        if (this.gameLogic.applyAction(currentState, action)) {
            notifyPlayersActionSuccessfullyExecuted();
            if (this.gameLogic.isEndState(currentState)) {
                gameStatus = END_STATUS;
                notifyPlayersGameEnd();
            } else {
                this.players[getCurrentTurn()].onSelectAction(currentState, this.gameLogic.getValidActions(currentState));
            }
            return true;
        }
        return false;
    }

    private void notifyPlayersGameEnd() {
        for (Player player : players)
            player.onGameEnd();
    }

    private void notifyPlayersActionSuccessfullyExecuted() {
        for (Player player : players)
            player.onActionSuccessfullyExecuted();
    }

}

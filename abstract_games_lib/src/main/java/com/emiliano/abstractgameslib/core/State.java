package com.emiliano.abstractgameslib.core;

public interface State extends Cloneable {

    int getTurn();

    State clone();

}

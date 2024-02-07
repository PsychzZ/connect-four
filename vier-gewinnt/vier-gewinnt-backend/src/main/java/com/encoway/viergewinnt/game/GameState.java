package com.encoway.viergewinnt.game;

import com.encoway.viergewinnt.dto.Player;

public class GameState {

    private static final int COLUMN = 7;

    private static final int ROW = 7;

    private Player winner = null;

    private boolean draw = false;

    private Player[][] field = new Player[ROW][COLUMN];

    private Player currentPlayer = Player.X;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public Player[][] getField() {
        return field;
    }

    public void setField(int row, int column, Player value) {
        field[row][column] = value;
    }

    public void setGameField(Player[][] gameField) {
        field = gameField;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }


    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public void switchPlayer() {
        this.currentPlayer = currentPlayer.other();
    }

}

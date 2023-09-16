package com.example.madassignment1;

import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BoardViewModel extends ViewModel {
    // only modified by the board fragment
    public ArrayList<ArrayList<ImageButton>> board = new ArrayList<>();
    // false means player one, true means player 2
    public MutableLiveData<Boolean> turnOver = new MutableLiveData<>(false);


    // defaults values
    // with thread safe live data
    private MutableLiveData<String> username = new MutableLiveData<>("");
    private MutableLiveData<ArrayList<String>> usernameList = new MutableLiveData<>();
    private LiveData<LinearLayout> boardLayout;
    // keeps track of the winner. 0 = tie, 1 = player 1, 2 = player 2

    private MutableLiveData<Integer> winner = new MutableLiveData<>(0);
    private MutableLiveData<Integer> gamesWon = new MutableLiveData<>(0);
    private MutableLiveData<Integer> gamesLost = new MutableLiveData<>(0);
    private MutableLiveData<Integer> gamesTied = new MutableLiveData<>(0);
    private MutableLiveData<Integer> gamesPlayed = new MutableLiveData<>(0);
    private MutableLiveData<Integer> movesAvailable = new MutableLiveData<>(9);
    private MutableLiveData<Integer> movesMade = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> tie = new MutableLiveData<>(false);
    private LiveData<Integer> lastMoveX = new MutableLiveData<>(-1);
    private LiveData<Integer> lastMoveY = new MutableLiveData<>(-1);
    private MutableLiveData<Boolean> ai = new MutableLiveData<>(false);

    private LiveData<Integer> player1Marker = new MutableLiveData<>(R.drawable.x);
    private LiveData<Integer> player2Marker = new MutableLiveData<>(R.drawable.o);

    private MutableLiveData<Integer> boardSize = new MutableLiveData<>(3);
    private MutableLiveData<Integer> winCondition = new MutableLiveData<>(3);
    private LiveData<Boolean> undoUsed = new MutableLiveData<>(false);

    public LiveData<Boolean> getTie() {
        return tie;
    }

    public boolean isTie() {
        return tie.getValue();
    }

    public void setTie(boolean tie) {
        this.tie.setValue(tie);
    }

    public LiveData<Integer> getMovesAvailable() {
        return movesAvailable;
    }

    public LiveData<Integer> getMovesMade() {
        return movesMade;
    }

    public boolean hasLayout() {
        return boardLayout != null;
    }

    public LinearLayout getBoardLayout() {
        return boardLayout.getValue();
    }

    public void setBoardLayout(LinearLayout boardLayout) {
        this.boardLayout = new MutableLiveData<>(boardLayout);
    }

    public int getBoardSize() {
        return boardSize.getValue();
    }

    public void setBoardSize(int boardSize) {
        // changing the board size will reset everything
        this.boardSize.setValue(boardSize);
        this.movesAvailable.setValue(boardSize * boardSize);
        resetBoard();
    }

    public void resetBoard() {
        this.movesAvailable.setValue(boardSize.getValue() * boardSize.getValue());
        this.movesMade.setValue(0);
        this.tie.setValue(false);
        this.gameOver = new MutableLiveData<>(false);
        this.lastMoveX = new MutableLiveData<>(-1);
        this.lastMoveY = new MutableLiveData<>(-1);
        this.turnOver = new MutableLiveData<>(false);
        boardLayout = null;
    }

    public int getWinCondition() {
        return winCondition.getValue();
    }

    public void setWinCondition(int winCondition) {
        // changing the win condition will reset everything
        assert winCondition <= boardSize.getValue();
        this.winCondition.setValue(winCondition);
        resetBoard();

    }

    public boolean isAi() {
        return ai.getValue();
    }

    public void setAi(boolean value) {
        this.ai.setValue(value);
    }


    public int getPlayer1Marker() {
        return player1Marker.getValue();
    }

    public void setPlayer1Marker(int player1Marker) {
        this.player1Marker = new MutableLiveData<>(player1Marker);
    }

    public int getPlayer2Marker() {
        return player2Marker.getValue();
    }

    public void setPlayer2Marker(int player2Marker) {
        this.player2Marker = new MutableLiveData<>(player2Marker);
    }

    public void decrementMovesAvailable() {
        this.movesAvailable.setValue(movesAvailable.getValue() - 1);
    }

    public void resetMovesAvailable() {
        this.movesAvailable.setValue(boardSize.getValue() * boardSize.getValue());
    }

    public void incrementMovesMade() {
        this.movesMade.setValue(movesMade.getValue() + 1);
    }

    public void resetMovesMade() {
        this.movesMade.setValue(0);
    }

    public boolean isGameOver() {
        return gameOver.getValue();
    }

    public LiveData<Boolean> getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver.setValue(gameOver);
    }

    public int getLastMoveX() {
        return lastMoveX.getValue();
    }

    public void setLastMoveX(int lastMoveX) {
        this.lastMoveX = new MutableLiveData<>(lastMoveX);
    }

    public int getLastMoveY() {
        return lastMoveY.getValue();
    }

    public void setLastMoveY(int lastMoveY) {
        this.lastMoveY = new MutableLiveData<>(lastMoveY);
    }

    public boolean isTurnOver() {
        return turnOver.getValue();
    }

    public void setTurnOver() {
        this.turnOver.setValue(!turnOver.getValue());
    }

    public LiveData<Integer> getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int value) {
        gamesWon.setValue(value);
    }

    public LiveData<Integer> getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int value) {
        gamesLost.setValue(value);
    }

    public LiveData<Integer> getGamesTied() {
        return gamesTied;
    }

    public void setGamesTied(int value) {
        gamesTied.setValue(value);
    }

    public LiveData<Integer> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int value) {
        gamesPlayed.setValue(value);
    }
    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username.setValue(value);
    }
    public MutableLiveData<ArrayList<String>> getUsernameList() {
        if (usernameList.getValue() == null) {
            usernameList.setValue(new ArrayList<>());
        }
        return usernameList;
    }

    public void addUsername(String value) {
        ArrayList<String> currentList = usernameList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(value);
        usernameList.setValue(currentList);
    }

    public LiveData<Integer> getWinner() {
        return winner;
    }

    public void setWinner(int value) {
        winner.setValue(value);
    }


    public void incrementMovesAvailable() {
        this.movesAvailable.setValue(movesAvailable.getValue() + 1);
    }

    public void decrementMovesMade() {
        this.movesMade.setValue(movesMade.getValue() - 1);
    }
}

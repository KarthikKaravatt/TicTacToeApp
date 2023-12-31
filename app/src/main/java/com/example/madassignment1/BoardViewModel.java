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
    private final MutableLiveData<String> username = new MutableLiveData<>("");
    private final MutableLiveData<ArrayList<String>> usernameList = new MutableLiveData<>();
    private LiveData<LinearLayout> boardLayout;
    // keeps track of the winner. 0 = tie, 1 = player 1, 2 = player 2
    private final MutableLiveData<Boolean> gamePaused = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> winner = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> gamesWon = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> gamesLost = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> gamesTied = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> gamesPlayed = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> movesAvailable = new MutableLiveData<>(9);
    private final MutableLiveData<Integer> movesMade = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> tie = new MutableLiveData<>(false);
    private LiveData<Integer> lastMoveX = new MutableLiveData<>(-1);
    private LiveData<Integer> lastMoveY = new MutableLiveData<>(-1);
    private final MutableLiveData<Boolean> ai = new MutableLiveData<>(false);

    private LiveData<Integer> currentPlayer1Marker = new MutableLiveData<>(R.drawable.x);
    private LiveData<Integer> currentPlayer2Marker = new MutableLiveData<>(R.drawable.o);
    private LiveData<Integer> player1Marker = new MutableLiveData<>(R.drawable.x);
    private LiveData<Integer> player2Marker = new MutableLiveData<>(R.drawable.o);

    private final MutableLiveData<Integer> boardSize = new MutableLiveData<>(3);
    private final MutableLiveData<Integer> winCondition = new MutableLiveData<>(3);
    private final MutableLiveData<Boolean> undoUsed = new MutableLiveData<>(false);
    public LiveData<Boolean> getUndoUsed() {
        return undoUsed;
    }
    public void setUndoUsed(boolean undoUsed) {
        this.undoUsed.setValue(undoUsed);
    }

    public LiveData<Boolean> getGamePaused() {
        return gamePaused;
    }
    public void setGamePaused(boolean gamePaused) {
        this.gamePaused.setValue(gamePaused);
    }
    public LiveData<Integer> getCurrentPlayer1Marker() {
        return currentPlayer1Marker;
    }
    public void setCurrentPlayer1Marker(int currentPlayer1Marker) {
        this.currentPlayer1Marker = new MutableLiveData<>(currentPlayer1Marker);
    }
    public LiveData<Integer> getCurrentPlayer2Marker() {
        return currentPlayer2Marker;
    }
    public void setCurrentPlayer2Marker(int currentPlayer2Marker) {
        this.currentPlayer2Marker = new MutableLiveData<>(currentPlayer2Marker);
    }

    public LiveData<Boolean> getTie() {
        return tie;
    }

    public boolean isTie() {
        if (tie.getValue() == null) {
            return false;
        }
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
        assert boardSize.getValue() != null;
        return boardSize.getValue();
    }

    public void setBoardSize(int boardSize) {
        // changing the board size will reset everything
        this.boardSize.setValue(boardSize);
        this.movesAvailable.setValue(boardSize * boardSize);
        resetBoard();
    }

    public void resetBoard() {
        assert boardSize.getValue() != null;
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
        assert winCondition.getValue() != null;
        return winCondition.getValue();
    }

    public void setWinCondition(int winCondition) {
        // changing the win condition will reset everything
        assert boardSize.getValue() != null;
        assert winCondition <= boardSize.getValue();
        this.winCondition.setValue(winCondition);
        resetBoard();

    }

    public boolean isAi() {
        assert ai.getValue() != null;
        return ai.getValue();
    }

    public void setAi(boolean value) {
        this.ai.setValue(value);
    }


    public int getPlayer1Marker() {
        assert player1Marker.getValue() != null;
        return player1Marker.getValue();
    }

    public void setPlayer1Marker(int player1Marker) {
        this.player1Marker = new MutableLiveData<>(player1Marker);
    }

    public int getPlayer2Marker() {
        assert player2Marker.getValue() != null;
        return player2Marker.getValue();
    }

    public void setPlayer2Marker(int player2Marker) {
        this.player2Marker = new MutableLiveData<>(player2Marker);
    }

    public void decrementMovesAvailable() {
        assert movesAvailable.getValue() != null;
        this.movesAvailable.setValue(movesAvailable.getValue() - 1);
    }

    public void resetMovesAvailable() {
        assert boardSize.getValue() != null;
        this.movesAvailable.setValue(boardSize.getValue() * boardSize.getValue());
    }

    public void incrementMovesMade() {
        assert movesMade.getValue() != null;
        this.movesMade.setValue(movesMade.getValue() + 1);
    }

    public void resetMovesMade() {
        this.movesMade.setValue(0);
    }

    public boolean isGameOver() {
        assert gameOver.getValue() != null;
        return gameOver.getValue();
    }

    public LiveData<Boolean> getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver.setValue(gameOver);
    }

    public int getLastMoveX() {
        assert lastMoveX.getValue() != null;
        return lastMoveX.getValue();
    }

    public void setLastMoveX(int lastMoveX) {
        this.lastMoveX = new MutableLiveData<>(lastMoveX);
    }

    public int getLastMoveY() {
        assert lastMoveY.getValue() != null;
        return lastMoveY.getValue();
    }

    public void setLastMoveY(int lastMoveY) {
        this.lastMoveY = new MutableLiveData<>(lastMoveY);
    }

    public boolean isTurnOver() {
        assert turnOver.getValue() != null;
        return turnOver.getValue();
    }

    public void setTurnOver() {
        assert turnOver.getValue() != null;
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
        assert movesAvailable.getValue() != null;
        this.movesAvailable.setValue(movesAvailable.getValue() + 1);
    }

    public void decrementMovesMade() {
        assert movesMade.getValue() != null;
        this.movesMade.setValue(movesMade.getValue() - 1);
    }
}

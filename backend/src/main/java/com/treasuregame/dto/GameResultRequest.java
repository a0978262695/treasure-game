package com.treasuregame.dto;

public class GameResultRequest {

    private int finalScore;
    private boolean foundTreasure;
    private int boxesOpened;

    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
    public boolean isFoundTreasure() { return foundTreasure; }
    public void setFoundTreasure(boolean foundTreasure) { this.foundTreasure = foundTreasure; }
    public int getBoxesOpened() { return boxesOpened; }
    public void setBoxesOpened(int boxesOpened) { this.boxesOpened = boxesOpened; }
}

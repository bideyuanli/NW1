package com.bideyuanli.numberwar;

/**
 * Created by Siyi on 2014/11/19.
 */
public class NumberModel {
    static private NumberModel instance;
    private int width = 6;
    private int height = 6;
    private int step = 0;
    private int score = 0;
    private int next = 1;
    private int current = 1;

    private int[][] grid;
    private int[][] next_step = {
            {1, 80},
            {2, 14},
            {3, 5},
            {4, 1}
    };

    static public NumberModel get() {
        if (instance == null) {
            instance = new NumberModel();
        }
        return instance;
    }

    private NumberModel() {
        reset();
    }

    public void reset() {
        grid = new int[width][height];
        step = 0;
        score = 0;
        next = 1;
        current = 1;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getNext_step() {
        return next_step;
    }

    public void setNext_step(int[][] next_step) {
        this.next_step = next_step;
    }
}

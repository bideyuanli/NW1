package com.bideyuanli.numberwar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Siyi on 2014/11/19.
 */
public class NumberModel {
    static public final int CURRENT_INDEX = -10;
    static private NumberModel instance;
    private int width = 5;
    private int height = 5;
    private int step = 0;
    private int score = 0;
    private int next = 1;
    private int current = 1;
    private Random r = new Random();

    private int[][] grid;
    private int[][] next_step = {
            {1, 80},
            {2, 14},
            {3, 5},
            {4, 1}
    };
    private int next_step_size;

    private NumberModel() {
        reset();
    }

    static public NumberModel get() {
        if (instance == null) {
            instance = new NumberModel();
        }
        return instance;
    }

    public AnimationModel clickNumber(int index) {
        if (getGrid(index) > 0) return null;

        setGrid(index, current);
        current = next;
        next  = generateNext();
        AnimationModel am = new AnimationModel();
        am.setFrom(CURRENT_INDEX);
        am.setTo(index);
        return am;
    }

    public AnimationModel calculate(int index) {
        int value = getGrid(index);
        List<Integer> queue = new ArrayList<Integer>();
        queue.add(index);
        for (int i = 0; i < queue.size(); ++i) {
            int current = queue.get(i);
            for (int next : getNearTiles(current)) {
                if (queue.contains(next)) continue;
                if (getGrid(next) == value) {
                    queue.add(next);
                }
            }
        }
        if (queue.size() >= 3) {
            setGrid(index, value + 1);
            queue.remove(0);
            for (int i : queue) {
                setGrid(i, 0);
            }
            AnimationModel am = new AnimationModel();
            am.setFroms(queue);
            am.setTo(index);
            return am;
        }
        return null;
    }

    private List<Integer> getNearTiles(int index) {
        List<Integer> queue = new ArrayList<Integer>();
        if (index % width > 0) queue.add(index - 1);
        if (index % width < width - 1) queue.add(index + 1);
        if (index > width) queue.add(index - width);
        if (index + width < getSize()) queue.add(index + width);
        return queue;
    }

    public void reset() {
        grid = new int[width][height];
        step = 0;
        score = 0;
        next_step_size = 0;
        for (int i = 0; i < next_step.length; i++) {
            next_step_size += next_step[i][1];
        }
        next = generateNext();
        current = generateNext();
    }

    public int generateNext() {
        int value = r.nextInt(next_step_size);

        for (int i = 0; i < next_step.length; i++) {
            if (value < next_step[i][1]) return next_step[i][0];
            value -= next_step[i][1];
        }
        return 0;
    }

    public int getSize(){ return width * height;}

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

    public int getGrid(int index) {
        if (index == CURRENT_INDEX) return current;
        return grid[index % width][index / width];
    }

    public void setGrid(int index, int value) {
        grid[index % width][index / width] = value;
    }

    public int[][] getNext_step() {
        return next_step;
    }

    public void setNext_step(int[][] next_step) {
        this.next_step = next_step;
    }
}

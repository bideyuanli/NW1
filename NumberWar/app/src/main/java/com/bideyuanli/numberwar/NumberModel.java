package com.bideyuanli.numberwar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Siyi on 2014/11/19.
 */
public class NumberModel {
    static public final int VALUE_ALL = -1;
    private int[][] next_step = {
            {1, 100, 0xFFECB3},
            {2, 30, 0xFFE082},
            {3, 8, 0xFFD54F},
            {4, 2, 0xFFCA28},
            {VALUE_ALL, 1, 0xCDDC39},
            {0, 0, 0xFFFFFF},
            {5, 0, 0xFFC107},
            {6, 0, 0xFFB300},
            {7, 0, 0xFFA000},
            {8, 0, 0xFBC02D},
            {9, 0, 0xFF8F00},
            {10, 0, 0xFF6F00},
    };
    static public final int CURRENT_INDEX = -10;
    static public final int NEXT_INDEX = -20;
    static private NumberModel instance;
    private int width = 5;
    private int height = 5;
    private int step = 0;
    private int score = 0;
    private int next = 1;
    private int current = 1;
    private Random r = new Random();
    private boolean in_animation = false;
    private int[][] grid;
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
        if (in_animation) return null;
        if (getGrid(index) > 0) return null;

        if (current == VALUE_ALL) {
            int value = 1;
            List<Integer> near_values = new ArrayList<Integer>();
            for (int i : getNearTiles(index)) {
                int near_value = getGrid(i);
                if (near_value == 0) continue;
                if (getSameTiles(index, near_value).size() >= 2) {
                    near_values.add(getGrid(i));
                }
            }
            if (near_values.size() > 0) {
                Collections.sort(near_values);
                Collections.reverse(near_values);
                value = near_values.get(0);
                for (int i = 1; i < near_values.size(); i++) {
                    int next_value = near_values.get(i);
                    if (next_value == value - 1) {
                        value--;
                    } else if (next_value < value - 1) {
                        break;
                    }
                }
            }
            current = value;
        }

        setGrid(index, current);
        current = next;
        next = generateNext();
        AnimationModel am = new AnimationModel();
        am.setFrom(CURRENT_INDEX);
        am.setTo(index);
        in_animation = true;
        return am;
    }

    public AnimationModel calculate(int index) {
        int value = getGrid(index);
        List<Integer> queue = getSameTiles(index, value);
        if (queue.size() >= 2) {
            setGrid(index, value + 1);
            for (int i : queue) {
                setGrid(i, 0);
            }
            AnimationModel am = new AnimationModel();
            am.setFroms(queue);
            am.setTo(index);
            return am;
        }
        in_animation = false;
        return null;
    }

    private List<Integer> getSameTiles(int index, int value) {
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
        queue.remove(0);
        return queue;
    }

    private List<Integer> getNearTiles(int index) {
        List<Integer> queue = new ArrayList<Integer>();
        if (index % width > 0) queue.add(index - 1);
        if (index % width < width - 1) queue.add(index + 1);
        if (index >= width) queue.add(index - width);
        if (index + width < getSize()) queue.add(index + width);
        return queue;
    }

    public void reset() {
        grid = new int[width][height];
        step = 0;
        score = 0;
        next_step_size = 0;
        in_animation = false;
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

    public int getColor(int value) {
        for (int i = 0; i < next_step.length; i++) {
            if (value == next_step[i][0]) {
                return next_step[i][2];
            }
        }
        return next_step[next_step.length - 1][2];
    }

    public int getSize() {
        return width * height;
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

    public int getGrid(int index) {
        if (index == CURRENT_INDEX) return current;
        return grid[index % width][index / width];
    }

    public void setGrid(int index, int value) {
        grid[index % width][index / width] = value;
        score += value * 10;
    }

    public int[][] getNext_step() {
        return next_step;
    }

    public void setNext_step(int[][] next_step) {
        this.next_step = next_step;
    }
}

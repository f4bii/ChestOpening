package de.f4bii.api.gui;

import java.util.ArrayList;
import java.util.List;

public enum FillerOptions {
    TOP(size -> new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }),
    BOTTOM(size -> {
        int i1 = size/9-1;
        int[] out = new int[9];
        for (int i = 0; i < 9; i++) {
            out[i] = 9*i1+i;
        }
        return out;
    }),
    RIGHT(size -> {
        int i1 = size/9;
        int[] out = new int[i1];
        for (int i = 0; i < i1; i++) {
            out[i] = i*9+8;
        }
        return out;
    }),
    LEFT(size -> {
        int i1 = size/9;
        int[] out = new int[i1];
        for (int i = 0; i < i1; i++) {
            out[i] = i*9;
        }
        return out;
    }),
    FILL(size -> {
        int[] out = new int[size];
        for (int i = 0; i < size; i++) {
            out[i] = i;
        }
        return out;
    }),
    OUTSIDE(size -> {
        List<Integer> slots = new ArrayList<>();
        slots.addAll(TOP.getAll(size));
        slots.addAll(BOTTOM.getAll(size));
        slots.addAll(RIGHT.getAll(size));
        slots.addAll(LEFT.getAll(size));
        int[] out = new int[slots.size()];
        for (int i = 0; i < slots.size(); i++) {
            out[i] = slots.get(i);
        }
        return out;
    });

    private final OptionsExecute optionsExecute;

    FillerOptions(OptionsExecute optionsExecute) {
        this.optionsExecute = optionsExecute;
    }

    private List<Integer> getAll(int size) {
        List<Integer> out = new ArrayList<>();
        int[] execute = optionsExecute.execute(size);
        for (int j : execute) {
            out.add(j);
        }
        return out;
    }

    public int[] execute(int size) {
        return optionsExecute.execute(size);
    }

    public interface OptionsExecute {

        int[] execute(int size);
    }
}

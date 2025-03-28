package com.project.util;

public class ComboBoxIndexer {
    private String display;
    private int value;

    public ComboBoxIndexer(String display, int value) {
        this.display = display;
        this.value = value;
    }

    @Override
    public String toString() {
        return display;
    }

    public int getValue() {
        return value;
    }
}

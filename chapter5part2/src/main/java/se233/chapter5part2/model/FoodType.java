package se233.chapter5part2.model;

import javafx.scene.paint.Color;

public enum FoodType {
    NORMAL(1, Color.RED),
    SPECIAL(5, Color.GREEN);

    public final int score;
    public final Color color;

    FoodType(int score, Color color) {
        this.score = score;
        this.color = color;
    }
}

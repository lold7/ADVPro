package se233.chapter5part2.model;

import javafx.geometry.Point2D;
import se233.chapter5part2.view.GameStage;

import java.util.Random;

public class Food {
    private Point2D position;
    private Random rn;
    private FoodType type;

    public Food(Point2D position, FoodType type) {
        this.rn = new Random();
        this.position = position;
        this.type = type;
    }

    // Constructor for testing
    public Food(Point2D position) {
        this.rn = new Random();
        this.position = position;
        this.type = FoodType.NORMAL;
    }

    public Food() {
        this.rn = new Random();
        respawn(); // Call respawn to set initial position and type
    }

    public void respawn() {
        // Set new position
        Point2D prev_position = this.position;
        do {
            this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        } while (this.position.equals(prev_position));

        // Set new type (20% chance for special food)
        if (rn.nextDouble() < 0.2) { // 20% chance
            this.type = FoodType.SPECIAL;
        } else {
            this.type = FoodType.NORMAL;
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public FoodType getType() {
        return type;
    }

    public int getScore() { return type.score; }
}

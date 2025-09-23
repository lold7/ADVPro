package se233.chapter5part2.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import se233.chapter5part2.model.Direction;
import se233.chapter5part2.model.Food;
import se233.chapter5part2.model.Snake;
import se233.chapter5part2.view.GameStage;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private Snake snake;
    private Food food;
    private float interval = 1000.0f / 10;
    private boolean running;
    public GameLoop(GameStage gameStage, Snake snake, Food food) {
        this.gameStage = gameStage;
        this.snake = snake;
        this.food = food;
        running = true;
    }

    private void keyProcess() {
        KeyCode curKey = gameStage.getKey();
        Direction curDirection = snake.getDirection();
        if (curKey == KeyCode.UP && curDirection != Direction.DOWN) {
            snake.setDirection(Direction.UP);
        } else if (curKey == KeyCode.DOWN && curDirection != Direction.UP) {
            snake.setDirection(Direction.DOWN);
        } else if (curKey == KeyCode.LEFT && curDirection != Direction.RIGHT) {
            snake.setDirection(Direction.LEFT);
        } else if (curKey == KeyCode.RIGHT && curDirection != Direction.LEFT) {
            snake.setDirection(Direction.RIGHT);
        }
        snake.move();
    }

    private void checkCollision() {
        if (snake.collided(food)) {
            snake.eatFood(food); // Pass food to eatFood method
            food.respawn();
        }
        if (snake.checkDead()) {
            running = false;
        }
    }

    private void redraw() { gameStage.render(snake, food); }

    private void showGameOver() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Game Over! Your score: " + snake.getScore());
            alert.showAndWait();
        });
    }

    @Override
    public void run() {
        while (running) {
            keyProcess();
            checkCollision();
            if (running) {
                redraw();
            }
            try {
                Thread.sleep((long)interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Show Game Over pop-up after the loop terminates
        showGameOver();
    }
}

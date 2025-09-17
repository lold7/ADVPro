package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.model.Keys;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private Image gameStageImg;
    private List<GameCharacter> characters;
    private Keys keys;

    public GameStage() {
        keys = new Keys();
        characters = new ArrayList<>();

        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        // Create Player 1 (Mario) using the new constructor
        GameCharacter character1 = new GameCharacter(30, 30, KeyCode.A, KeyCode.D, KeyCode.W,
                "assets/MarioSheet.png", 0, 0,
                4, 4, 1, 16, 32,
                32, 64);

        // Create Player 2 (Rockman) using the new constructor and rockman.png
        GameCharacter character2 = new GameCharacter(90, 30, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP,
                "assets/rockman.png", 0, 0,
                4, 3, 1, 500, 500,
                64, 64);


        // Set distinct max velocities for the second character
        character2.setxMaxVelocity(5);
        character2.setyMaxVelocity(20);

        characters.add(character1);
        characters.add(character2);

        getChildren().addAll(backgroundImg);
        getChildren().addAll(characters);
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public Keys getKeys() {
        return keys;
    }
}
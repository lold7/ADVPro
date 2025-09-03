package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.model.Keys;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private Image gameStageImg;
    private GameCharacter gameCharacter;

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public void setGameCharacter(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    public Image getGameStageImg() {
        return gameStageImg;
    }

    public void setGameStageImg(Image gameStageImg) {
        this.gameStageImg = gameStageImg;
    }

    private Keys keys;
    public GameStage() {
        keys = new Keys();
        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        gameCharacter = new GameCharacter(30, 30,0,0, KeyCode.A,KeyCode.D,KeyCode.W);
        getChildren().addAll(backgroundImg, gameCharacter);
    }
    public GameCharacter getGameCharacter() {
        return gameCharacter;
    }
    public Keys getKeys() {
        return keys;
    }
}

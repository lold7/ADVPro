package se233.chapter4.model;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;

public class GameCharacter extends Pane {
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image gameCharacterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity=0;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    int yVelocity = 3;
    boolean isFalling = true;
    boolean canJump= false;
    boolean isJumping =false;
    int xAcceleration =1;
    int yAcceleration =1;

    public Image getGameCharacterImg() {
        return gameCharacterImg;
    }

    public void setGameCharacterImg(Image gameCharacterImg) {
        this.gameCharacterImg = gameCharacterImg;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public void setImageView(AnimatedSprite imageView) {
        this.imageView = imageView;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(KeyCode leftKey) {
        this.leftKey = leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public void setRightKey(KeyCode rightKey) {
        this.rightKey = rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public void setUpKey(KeyCode upKey) {
        this.upKey = upKey;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        isMoveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setMoveRight(boolean moveRight) {
        isMoveRight = moveRight;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public int getxAcceleration() {
        return xAcceleration;
    }

    public void setxAcceleration(int xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public int getyAcceleration() {
        return yAcceleration;
    }

    public void setyAcceleration(int yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public int getxMaxVelocity() {
        return xMaxVelocity;
    }

    public void setxMaxVelocity(int xMaxVelocity) {
        this.xMaxVelocity = xMaxVelocity;
    }

    public int getyMaxVelocity() {
        return yMaxVelocity;
    }

    public void setyMaxVelocity(int yMaxVelocity) {
        this.yMaxVelocity = yMaxVelocity;
    }

    int xMaxVelocity=7;
    int yMaxVelocity=17;
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);


    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.gameCharacterImg = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
        this.imageView = new AnimatedSprite(gameCharacterImg, 4, 4, 1, offsetX, offsetY, 16, 32);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }
    public void moveX() {
        setTranslateX(x);
        if(isMoveLeft) {
            xVelocity =xVelocity>=xMaxVelocity? xMaxVelocity: xVelocity+xAcceleration;
            x = x - xVelocity; }
        if(isMoveRight) {
            xVelocity =xVelocity>=xMaxVelocity? xMaxVelocity: xVelocity+xAcceleration;
            x = x + xVelocity; }
    }
    public void moveY() {
        setTranslateY(y);
        if(isFalling) {
            yVelocity =yVelocity >=yMaxVelocity?yMaxVelocity :yVelocity+yAcceleration;
            y = y + yVelocity;
        }else if(isJumping){
            yVelocity =yVelocity <=0?0: yVelocity-yAcceleration;
            y= y-yVelocity;
        }
    }
    public void checkReachGameWall() {
        if(x <= 0) {
            x = 0;
        } else if( x+getWidth() >= GameStage.WIDTH) {
            x = GameStage.WIDTH-(int)getWidth();
        }
    }
    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void checkReachHighest () {
        if(isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor() {
        if(isFalling && y >= GameStage.GROUND- CHARACTER_HEIGHT) {
            isFalling = false;
            canJump= true;
        }
    }
    public void repaint() {
        moveX();
        moveY();
    }
    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft= true;
        isMoveRight= false;
    }
    public void moveRight() {
        setScaleX(1);
        isMoveLeft= false;
        isMoveRight= true;
    }
    public void stop() {
        isMoveLeft= false;
        isMoveRight= false;
    }
    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}",x,y,xVelocity,yVelocity);
    }

}

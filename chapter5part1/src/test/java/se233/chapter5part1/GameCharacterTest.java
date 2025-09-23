package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    Field xVelocityField, yVelocityField, yAccelerationField, canJumpField, isJumpingField, isFallingField, isMoveLeftField, isMoveRightField;

    private GameCharacter gameCharacter;
    private GameCharacter character2;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        gameCharacter = new GameCharacter(0, 30, 30, "assets/Character1.png", 4, 3, 2, 111, 97, KeyCode.A, KeyCode.D, KeyCode.W);
        character2 = new GameCharacter(1, 200, 200, "assets/Character2.png", 4, 4, 1, 129, 66, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);

        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        canJumpField = gameCharacter.getClass().getDeclaredField("canJump");
        isJumpingField = gameCharacter.getClass().getDeclaredField("isJumping");
        isFallingField = gameCharacter.getClass().getDeclaredField("isFalling");
        isMoveLeftField = gameCharacter.getClass().getDeclaredField("isMoveLeft");
        isMoveRightField = gameCharacter.getClass().getDeclaredField("isMoveRight");

        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
        canJumpField.setAccessible(true);
        isJumpingField.setAccessible(true);
        isFallingField.setAccessible(true);
        isMoveLeftField.setAccessible(true);
        isMoveRightField.setAccessible(true);
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws
            IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws
            IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAcceleration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAcceleration2 = yAccelerationField.getInt(gameCharacter);
        assertEquals(yAcceleration1, yAcceleration2, "Acceleration is not change");
    }


    @Test
    public void respawn_givenGameCharacter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30, gameCharacter.getX(), "Initial x");
        assertEquals(30, gameCharacter.getY(), "Initial y");
    }

    @Test
    public void respawn_givenGameCharacter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0, gameCharacter.getScore(), "Initial score");
    }

    @Test
    public void checkReachLeftWall_shouldSetXToZero() {
        // Arrange: Move character past the left boundary
        gameCharacter.setX(-50);

        // Act: Call the method to check wall collision
        gameCharacter.checkReachGameWall();

        // Assert: Character's x position should be corrected to 0
        assertEquals(0, gameCharacter.getX(), "Character should stop at the left wall boundary.");
    }

    @Test
    public void checkReachRightWall_shouldSetXToMaxBoundary() {
        // Arrange: Move character past the right boundary
        gameCharacter.setX(GameStage.WIDTH + 50);

        // Act: Call the method to check wall collision
        gameCharacter.checkReachGameWall();

        // Assert: Character's x position should be corrected to the maximum allowed position
        int expectedX = GameStage.WIDTH - gameCharacter.getCharacterWidth();
        assertEquals(expectedX, gameCharacter.getX(), "Character should stop at the right wall boundary.");
    }

    @Test
    public void jump_whenCanJump_shouldInitiateJump() throws IllegalAccessException {
        // Arrange: Set character state to be on the ground and able to jump
        canJumpField.setBoolean(gameCharacter, true);
        isJumpingField.setBoolean(gameCharacter, false);
        isFallingField.setBoolean(gameCharacter, false);

        // Act: Perform the jump
        gameCharacter.jump();

        // Assert: Verify the character's state has changed to jumping
        assertTrue(isJumpingField.getBoolean(gameCharacter), "Character should be in jumping state.");
        assertFalse(canJumpField.getBoolean(gameCharacter), "Character should not be able to jump again immediately.");
        assertFalse(isFallingField.getBoolean(gameCharacter), "Character should not be in falling state.");
    }

    @Test
    public void jump_whenCannotJump_shouldNotInitiateJump() throws IllegalAccessException {
        // Arrange: Set character state to be mid-air (falling) and unable to jump
        canJumpField.setBoolean(gameCharacter, false);
        isJumpingField.setBoolean(gameCharacter, false);
        isFallingField.setBoolean(gameCharacter, true);

        // Act: Attempt to jump
        gameCharacter.jump();

        // Assert: Verify the character's state remains unchanged
        assertFalse(isJumpingField.getBoolean(gameCharacter), "Character should not be in jumping state.");
        assertFalse(canJumpField.getBoolean(gameCharacter), "Character should still be unable to jump.");
        assertTrue(isFallingField.getBoolean(gameCharacter), "Character should still be in falling state.");
    }

    // In file: se233/chapter5part1/GameCharacterTest.java
// REPLACE the old horizontal collision test with this one:

    @Test
    public void collided_whenMovingRightIntoAnotherCharacter_stopsAtBoundary() throws IllegalAccessException {
        // Arrange: Position character1 to overlap with character2
        character2.setX(200);
        gameCharacter.setX(190); // Overlapping character2 from the left
        isMoveRightField.setBoolean(gameCharacter, true);

        // Act
        gameCharacter.collided(character2);

        // Assert: Character should be stopped exactly at the boundary of the other character
        int expectedX = character2.getX() - gameCharacter.getCharacterWidth();
        assertEquals(expectedX, gameCharacter.getX(), "Character should stop at the left boundary of the other character.");
        assertFalse(isMoveRightField.getBoolean(gameCharacter), "Character should have stopped moving right.");
    }

    // In file: se233/chapter5part1/GameCharacterTest.java

    @Test
    public void collided_whenFallingOntoAnotherCharacter_incrementsScoreAndReturnsTrue() throws IllegalAccessException {
        // Arrange
        gameCharacter.setX(200);
        character2.setX(200);
        gameCharacter.setY(190);
        character2.setY(200);
        isFallingField.setBoolean(gameCharacter, true);
        gameCharacter.setScore(0);

        // Act
        boolean result = gameCharacter.collided(character2);

        // Assert
        // THIS IS THE CORRECTED LINE:
        int expectedY = character2.getY() - gameCharacter.getCharacterHeight();
        assertTrue(result, "collided() should return true for a vertical collision.");
        assertEquals(1, gameCharacter.getScore(), "Score should be incremented by 1.");
        assertEquals(expectedY, gameCharacter.getY(), "Character should land on top of the other character.");
    }
}

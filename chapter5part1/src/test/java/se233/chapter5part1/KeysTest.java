package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.Keys;

import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {

    private Keys keys;

    @BeforeEach
    public void setUp() {
        keys = new Keys();
    }

    @Test
    public void singleKey_addAndRemove_shouldUpdateStateCorrectly() {
        // Arrange: A key that is initially not pressed
        KeyCode key = KeyCode.W;
        assertFalse(keys.isPressed(key), "Key should not be pressed initially.");

        // Act: Add the key
        keys.add(key);
        // Assert: The key is now pressed
        assertTrue(keys.isPressed(key), "isPressed should return true after adding a key.");

        // Act: Remove the key
        keys.remove(key);
        // Assert: The key is no longer pressed
        assertFalse(keys.isPressed(key), "isPressed should return false after removing the key.");
    }

    @Test
    public void multipleKeys_addAndRemove_shouldUpdateStatesIndependently() {
        // Arrange: Add two different keys
        KeyCode keyA = KeyCode.A;
        KeyCode keyD = KeyCode.D;
        KeyCode unpressedKey = KeyCode.SPACE;

        // Act: Add two keys
        keys.add(keyA);
        keys.add(keyD);

        // Assert: The two added keys are pressed, and the unpressed key is not
        assertTrue(keys.isPressed(keyA), "Key A should be pressed.");
        assertTrue(keys.isPressed(keyD), "Key D should be pressed.");
        assertFalse(keys.isPressed(unpressedKey), "An un-added key should not be pressed.");

        // Act: Remove one of the keys
        keys.remove(keyA);

        // Assert: The removed key is no longer pressed, but the other one remains pressed
        assertFalse(keys.isPressed(keyA), "Key A should no longer be pressed after removal.");
        assertTrue(keys.isPressed(keyD), "Key D should remain pressed after another key is removed.");
    }
}

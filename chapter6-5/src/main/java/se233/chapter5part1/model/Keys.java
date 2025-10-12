package se233.chapter5part1.model;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Keys {
    public HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }

    public void setKeys(HashMap<KeyCode, Boolean> keys) {
        this.keys = keys;
    }

    private  HashMap<KeyCode,Boolean> keys;
    public Keys() {
        keys = new HashMap<>();
    }
    public void add(KeyCode key) {
        keys.put(key, true);
    }
    public void remove(KeyCode key) {
        keys.put(key, false);
    }
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key,false);
    }
}
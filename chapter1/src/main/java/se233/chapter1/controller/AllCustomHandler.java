package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.character.Battlemage;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class AllCustomHandler {

    private static void unequipAllItems() {
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedCharacter character = Launcher.getMainCharacter();
        if (character == null) {
            return;
        }

        if (Launcher.getEquippedWeapon() != null) {
            allEquipments.add(Launcher.getEquippedWeapon());
            character.unequipWeapon();
            Launcher.setEquippedWeapon(null);
        }
        if (Launcher.getEquippedArmor() != null) {
            allEquipments.add(Launcher.getEquippedArmor());
            character.unequipArmor();
            Launcher.setEquippedArmor(null);
        }
    }

    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            unequipAllItems();
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    public static class UnequipAllHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            unequipAllItems();
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        if (!dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            return; // Do nothing if the content is not a game item
        }

        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
        BasedCharacter character = Launcher.getMainCharacter();

        // Logic for equipping Armor
        if (type.equals("Armor")) {
            if (!(retrievedEquipment instanceof Armor)) return;
            // Rule: Battlemage cannot equip any armor.
            if (character instanceof Battlemage) {
                return; // Disallow drop
            }
            // All other characters can equip armor.
            event.acceptTransferModes(TransferMode.MOVE);
        }

        // Logic for equipping Weapons
        if (type.equals("Weapon")) {
            if (!(retrievedEquipment instanceof Weapon)) return;
            Weapon draggedWeapon = (Weapon) retrievedEquipment;

            // Rule: Battlemage can equip any weapon type.
            if (character instanceof Battlemage) {
                event.acceptTransferModes(TransferMode.MOVE);
                return;
            }

            // Rule: Other character types must match the weapon's damage type.
            if (character.getType() == draggedWeapon.getDamageType()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            if(retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                character.equipWeapon((Weapon) retrievedEquipment);
            } else { // Armor
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size()!=1) {
                imgGroup.getChildren().remove(1);
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
            Dragboard dragboard = event.getDragboard();
            ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            int pos = -1;
            for (int i = 0; i < allEquipments.size(); i++) {
                if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                    pos = i;
                    break;
                }
            }
            if (pos != -1) {
                allEquipments.remove(pos);
            }
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
        }
    }
}
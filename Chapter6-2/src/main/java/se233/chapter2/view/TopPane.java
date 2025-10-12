package se233.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se233.chapter2.controller.AllEventHandlers;

import java.time.LocalDateTime;

/**
 * A JavaFX component that creates a top panel with refresh/change buttons
 * and an update timestamp.
 */
public class TopPane extends FlowPane {
    public Label getUpdate() {
        return update;
    }

    public void setUpdate(Label update) {
        this.update = update;
    }

    public Button getAdd() {
        return add;
    }

    public void setAdd(Button add) {
        this.add = add;
    }

    public Button getRefresh() {
        return refresh;
    }

    public void setRefresh(Button refresh) {
        this.refresh = refresh;
    }

    private Button refresh;
    private Button add;
    private Label update;

    public TopPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setPrefSize(640, 20);

        add = new Button("Add");
        refresh = new Button("Refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onRefresh();
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onAdd();
            }
        });
        update = new Label();

        refreshPane();

        this.getChildren().addAll(refresh, add, update);
    }

    public void refreshPane() {
        update.setText(String.format("Last update: %s", LocalDateTime.now().toString()));
    }
}

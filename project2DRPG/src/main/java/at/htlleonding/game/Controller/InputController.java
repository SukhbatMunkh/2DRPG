package at.htlleonding.game.Controller;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class InputController {
    //region <fields>
    private Scene scene;
    private SimpleListProperty input;
    private SimpleStringProperty mouseInput;

    public List<String> getInput() {
        return input;
    }

    public String getMouseInput() {
        return mouseInput.get();
    }
    //endregion

    public InputController(Scene scene) {
        this.scene = scene;
        this.scene.getRoot().requestFocus();
        this.input = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
        this.mouseInput = new SimpleStringProperty();

        this.scene.getRoot().setOnMouseClicked(mouseEvent -> mouseInput.set(mouseEvent.getEventType().toString()));

        this.scene.getRoot().setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().toString();

            if (!input.contains(code)) {
                input.add(code);
            }
        });

        this.scene.getRoot().setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().toString();

            if (input.get().size() > 0) {
                input.remove(code);
            }
        });
    }

    //region <Property-Getter>
    public SimpleListProperty<String> getInputProperty() {
        return input;
    }

    public SimpleStringProperty getMouseInputProperty() {
        return mouseInput;
    }
    //endregion
}

package at.htlleonding.game.Controller;

import javafx.scene.Scene;

public class InputController {
    private Scene scene;

    public InputController(Scene scene) {
        this.scene = scene;
        this.scene.getRoot().requestFocus();
        //check scene for action
    }
}

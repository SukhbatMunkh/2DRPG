package at.htlleonding.game.Controller;

import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.stage.Window;

public class GameScreen {
    private Canvas gameScreen;

    public GameScreen(Group group) {
        this.gameScreen = new Canvas();
        group.getChildren().add(this.gameScreen);
        adjustScreenSize();

        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);
    }

    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());
    }

    public void testMethod() {
        System.out.println(this.gameScreen.getWidth());
        System.out.println(this.gameScreen.getHeight());
        this.gameScreen.getGraphicsContext2D().fillRect(0,0,200,200);
    }

}

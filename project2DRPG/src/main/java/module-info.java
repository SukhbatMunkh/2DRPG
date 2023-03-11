module at.htlleonding.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens at.htlleonding.game to javafx.fxml;
    exports at.htlleonding.game;
}
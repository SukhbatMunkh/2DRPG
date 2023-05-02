module at.htlleonding.dungeonsandportals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens at.htlleonding.dungeonsandportals to javafx.fxml;
    exports at.htlleonding.dungeonsandportals;
}
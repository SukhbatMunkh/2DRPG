package at.htlleonding.dungeonsandportals.FXController;

import at.htlleonding.dungeonsandportals.Controller.GameScreen;
import at.htlleonding.dungeonsandportals.Controller.SceneLoader;
import at.htlleonding.dungeonsandportals.Model.Player;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartScreenController {
    @FXML
    private VBox backgroundNode;
    @FXML
    private Button startBtn;
    @FXML
    private ListView<Player> playerListView;

    private ObservableList<Player> playerObservableList;

    @FXML
    private void initialize() {
        //TODO: play some intro song background
        setRandomBackground();

        playerObservableList = FXCollections.observableList(DatabaseController.getPlayers());

        this.playerListView.setItems(playerObservableList);
    }

    private void setRandomBackground() {
        List<String> randomBackgroundOptions = new ArrayList<>();
        randomBackgroundOptions.add("Desert.png");
        randomBackgroundOptions.add("ForestPath.png");
        randomBackgroundOptions.add("Grass.png");
        randomBackgroundOptions.add("Market.png");

        String backgroundName = randomBackgroundOptions.get((new Random()).nextInt(3 + 1));

        Image background = GameScreen.getAnimationImages("background", backgroundName, false).get(0);

        backgroundNode.setBackground(new Background(new BackgroundImage(background, null, null, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    @FXML
    public void startBtnPressed(ActionEvent actionEvent) {
        Player player = playerListView.getSelectionModel().getSelectedItem();

        if (playerObservableList.contains(player)) {
            startBtn.setDisable(true); //to prevent it from being pressed twice
            SceneLoader.setPlayer(player); //To start everything
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a player!");
            alert.show();
        }
    }

    @FXML
    public void deleteBtnPressed(ActionEvent actionEvent) {
        Player player = playerListView.getSelectionModel().getSelectedItem();

        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please click on a player you want to delete");
            alert.show();
            return;
        }

        Label label = new Label("Do you really want to delete the player: " + player.getName() + "?");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");

        HBox hBox = new HBox(buttonYes, buttonNo);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = new VBox(label, hBox);

        backgroundNode.getChildren().add(vBox);

        buttonNo.setOnAction(actionEvent1 -> {
            backgroundNode.getChildren().remove(vBox);
        });

        buttonYes.setOnAction(actionEvent12 -> {
            boolean playerRemoved = DatabaseController.removePlayerByID(player.getId());
            Alert alert;

            if (playerRemoved) {
                playerObservableList.remove(player);
                alert = new Alert(Alert.AlertType.INFORMATION, "The player " + player.getName() + " was removed!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "There was a problem while removing the player: " + player.getName());
            }
            alert.show();
            backgroundNode.getChildren().remove(vBox);
        });
    }

    @FXML
    public void newPlayerBtnPressed(ActionEvent actionEvent) {
        Label label = new Label("Player-name:");
        label.setPadding(new Insets(0, 10, 0, 0));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
        TextField nameTextField = new TextField();
        Button createPlayerBtn = new Button("Create Player");

        HBox hBox = new HBox(label, nameTextField);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = new VBox(hBox, createPlayerBtn);

        backgroundNode.getChildren().add(vBox);

        createPlayerBtn.setOnAction(actionEvent1 -> {
            if (!nameTextField.getText().strip().equals("")){
                DatabaseController.createNewPlayer(nameTextField.getText());

                DatabaseController.getPlayers().forEach(p -> {
                    if (!playerObservableList.contains(p)) {
                        playerObservableList.add(p);
                        playerListView.getSelectionModel().select(p);
                    }
                });

                backgroundNode.getChildren().remove(vBox);
                startBtnPressed(null); // the actionevent isn't needed to start the game
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a name for the player");
                alert.show();
            }
        });
    }
}

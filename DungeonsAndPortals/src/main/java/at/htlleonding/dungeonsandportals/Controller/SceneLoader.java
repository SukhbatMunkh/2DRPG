package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.App;
import at.htlleonding.dungeonsandportals.Model.Direction;
import at.htlleonding.dungeonsandportals.Model.PlayerEntity;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.scene.Group;
import javafx.scene.Scene;

public class SceneLoader {
    //region <fields>
    private static SceneLoader instance;
    private GameScreen gameScreen;
    private static int playerID;
    private PlayerEntity player;
    private int curSceneID;
    //endregion

    //region <Getter and Setter>
    public static int getPlayerID() {
        return playerID;
    }

    public static void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    public static SceneLoader getInstance() {
        if (instance == null) {
            instance = new SceneLoader();
        }

        if (instance.gameScreen == null) {
            throw new RuntimeException("You first need to call the getInstance-Method with a group once");
        }

        instance.loadScene();

        return instance;
    }

    public GameScreen getGameScreen() {
        return instance.gameScreen;
    }
    //endregion

    private SceneLoader() {
        Group root = new Group();
        Scene scene = new Scene(root);
        App.getStage().setScene(scene);

        gameScreen = new GameScreen(root);
        curSceneID = 0;
        player = new PlayerEntity(SceneLoader.getPlayerID(), 2, scene);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("TODO: Saving the player"); //TODO: Save player in db
            }
        }, "Shutdown-thread"));

        curSceneID = DatabaseController.getPlayerStartSceneID();

        App.getStage().setHeight(1); //So that the gamescreen gets adjusted
        loadScene();
    }

    //region <Methods>
    public void moveToScene(Direction direction) {
        int[] dbDirection = Direction.getDirectionAsArray(direction);

        //TODO: dbController get next scene
        this.curSceneID = 0;

        loadScene();
    }

    private void loadScene() {
        gameScreen.deleteAllEntities();
        gameScreen.addEntity(player);

        gameScreen.setBackground(DatabaseController.getSceneBackground(curSceneID));
    }
    //endregion
}

package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.Model.Direction;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.scene.Group;

public class SceneLoader {
    //region <fields>
    private static SceneLoader instance;
    private GameScreen gameScreen;
    private int curSceneID;

    //endregion

    //region <Constructors>
    public static SceneLoader getInstance(Group group) {
        if (instance == null) {
            instance = new SceneLoader(group);
        }
        return getInstance();
    }

    public static SceneLoader getInstance() {
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

    private SceneLoader(Group group) {
        gameScreen = new GameScreen(group);
        curSceneID = 0;
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
        String backgroundName = DatabaseController.getSceneBackground(curSceneID);

        if (backgroundName == null) {
            throw new RuntimeException("The background Image wasn't found");
        }

        gameScreen.setBackground(instance.gameScreen.getAnimationImages("background", backgroundName, false).get(0));
    }
    //endregion
}

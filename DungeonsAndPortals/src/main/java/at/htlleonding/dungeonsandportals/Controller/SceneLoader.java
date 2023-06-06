package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.Model.Direction;
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
        if (instance == null) {
            throw new RuntimeException("You first need to call the getInstance-Method with a group once");
        }
        return instance;
    }
    //endregion

    private SceneLoader(Group group) {
        gameScreen = new GameScreen(group);
        curSceneID = 0;

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

        //TODO: get all objects from the db
    }
    //endregion
}

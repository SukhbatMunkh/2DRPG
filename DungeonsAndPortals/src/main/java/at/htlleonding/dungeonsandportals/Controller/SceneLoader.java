package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.App;
import at.htlleonding.dungeonsandportals.Model.*;
import at.htlleonding.dungeonsandportals.database.DatabaseController;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.List;

public class SceneLoader {
    //region <fields>
    private static SceneLoader instance;
    private GameScreen gameScreen;
    private Player player;
    private PlayerEntity playerEntity;
    private int curSceneID;
    //endregion

    //region <Getter and Setter>
    public static int getPlayerID() {
        return SceneLoader.getInstance().player.getId();
    }

    public static void setPlayer(Player newPlayer) {
        if (instance == null) {
            instance = new SceneLoader(newPlayer.getId());
        }

        SceneLoader.getInstance().player = newPlayer;

        if (SceneLoader.getInstance().playerEntity != null) {
            SceneLoader.getInstance().playerEntity.setPlayer(newPlayer);
        }
    }

    public static SceneLoader getInstance() {
        if (instance == null) {
            throw new RuntimeException("Please run the setPlayer Method first!");
        }

        if (instance.gameScreen == null) {
            throw new RuntimeException("You first need to call the getInstance-Method with a group once");
        }

        return instance;
    }

    public GameScreen getGameScreen() {
        return instance.gameScreen;
    }
    //endregion

    private SceneLoader(int playerID) {
        Group root = new Group();
        Scene scene = new Scene(root);
        App.getStage().setScene(scene);

        gameScreen = new GameScreen(root);

        playerEntity = new PlayerEntity(playerID, 2, scene);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Saving the player...");
                DatabaseController.savePlayer(player, curSceneID);
            }
        }, "Shutdown-thread"));

        curSceneID = DatabaseController.getPlayerStartSceneID(playerID);

        App.getStage().setHeight(1); //So that the gamescreen gets adjusted
        loadScene(curSceneID);
    }

    //region <Methods>
    public static void moveToScene(Direction direction) {
        int newScene = DatabaseController.getSceneViaDirection(direction, SceneLoader.getInstance().curSceneID);

        if (newScene != -1) {
            double height = 450; //TODO: adjust to screen size
            double width = 600;

            double playerHeight = SceneLoader.getInstance().playerEntity.getMovementController().getAnimation().getySize();
            double playerWidth = SceneLoader.getInstance().playerEntity.getMovementController().getAnimation().getxSize();

            double newXPos = 0;
            double newYPos = 0;

            switch (direction) {
                case NORTH -> {
                    newXPos = SceneLoader.getInstance().playerEntity.getMovementController().getxPosition();
                    newYPos = height-playerHeight-15;
                }
                case SOUTH -> {
                    newXPos = SceneLoader.getInstance().playerEntity.getMovementController().getxPosition();
                    newYPos = 15;
                }
                case WEST -> {
                    newXPos = width-playerWidth-15;
                    newYPos = SceneLoader.getInstance().playerEntity.getMovementController().getyPosition();
                }
                case EAST -> {
                    newXPos = 15;
                    newYPos = SceneLoader.getInstance().playerEntity.getMovementController().getyPosition();
                }
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }

            getInstance().playerEntity.getMovementController().setPlayerPosition(newXPos, newYPos);

            SceneLoader.getInstance().curSceneID = newScene;
            SceneLoader.getInstance().loadScene(SceneLoader.getInstance().curSceneID);
        }
    }

    /**
     * loads the specified scene by
     * getting all the objects and mobs and
     * everything from the db and putting
     * them into the gameScreen
     * @param sceneID
     */
    private void loadScene(int sceneID) {
        gameScreen.deleteAllEntities();
        gameScreen.addEntity(playerEntity);

        //TODO: remove hard-coded slime
        if (sceneID == 0) {
            gameScreen.addEntity(new Entity(100, new FrameAnimation(GameScreen.getAnimationImages("fire_slime", "fire_slime.png", true), 400, 200, 30, 50, 300, SceneLevel.BACKGROUND_OBJECTS), 2, App.getStage().getScene()) {
                @Override
                public void move(List<FrameAnimation> frameAnimations) {
                    return;
                }
            });

            gameScreen.addEntity(new Entity(200, new FrameAnimation(GameScreen.getAnimationImages("slime", "slimeJump.png", true), 200, 200, 50, 50, 200, SceneLevel.MOBS), 2, App.getStage().getScene()) {
                @Override
                public void move(List<FrameAnimation> frameAnimations) {
                    return;
                }
            });
        }

        gameScreen.setBackground(DatabaseController.getSceneBackground(sceneID));

        List<Direction> pathDirections = DatabaseController.getPathsAsDirection(sceneID);

        for (Direction direction : pathDirections) {
            System.out.println(direction);
            playerEntity.addPath(direction);
        }
        System.out.println("-------------");
    }
    //endregion
}

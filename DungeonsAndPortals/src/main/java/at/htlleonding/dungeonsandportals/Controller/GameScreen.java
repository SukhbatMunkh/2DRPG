package at.htlleonding.dungeonsandportals.Controller;

import at.htlleonding.dungeonsandportals.Model.Entity;
import at.htlleonding.dungeonsandportals.Model.FrameAnimation;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GameScreen {
    //region <fields>
    private Canvas gameScreen;
    private static String animationDirectory = GameScreen.class.getResource("img").toString().replace("file:/", "");

    private Image background;
    private List<Entity> entityList;
    //endregion

    //region <Getter and Setter>
    public Canvas getGameScreen() {
        return gameScreen;
    }

    public String getAnimationDirectory() {
        return animationDirectory;
    }

    public void setBackground(Image newBackground) {
        background = newBackground;
    }

    public Image getBackground() {
        return background;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void addEntity(Entity entity) {
        this.entityList.add(entity);
        entityList.sort(Entity::compareTo);
    }

    /**
     * Deletes all Entities with the id
     * @param id
     */
    public void deleteEntity(int id) {
        for (Entity entity : entityList.stream().filter(e -> e.getId() == id).toList()) {
            entityList.remove(entity);
        }
    }
    //endregion

    public GameScreen(Group group) {
        entityList = new ArrayList<>();
        this.gameScreen = new Canvas();
        group.getChildren().removeIf(f -> true); // to clear the screen of everything and start the game
        group.getChildren().add(this.gameScreen);

        // add listener so that the Canvas is always the size of the app
        InvalidationListener listener = o -> adjustScreenSize();
        this.gameScreen.getScene().getWindow().widthProperty().addListener(listener);
        this.gameScreen.getScene().getWindow().heightProperty().addListener(listener);

        startScreenThread();
    }

    //region <Methods>
    private void startScreenThread() {
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //region <clear screen>
                gameScreen.getGraphicsContext2D().clearRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
                gameScreen.getGraphicsContext2D().setFill(Color.BLACK);
                gameScreen.getGraphicsContext2D().fillRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
                //endregion

                //set background
                gameScreen.getGraphicsContext2D().drawImage(background, 0, 0, 600, 450); //TODO: ratio with screen size

                // getting the current time from the start of the AnimationTimer
                final long time = (currentNanoTime - startNanoTime);

                //region <load images>
                if (entityList.size() > 0) {
                    // load the images to the screen
                    for (int i = 0; i < entityList.size(); i++) {
                        Entity entity = entityList.get(i);
                        /* move each entity and give it a list of FrameAnimations
                         * (the other entitys) so that collisions are possible
                         * (naturally it doesn't get itself because it always
                         * "intersects" with itself)
                         */
                        entity.move(entityList.stream().filter(e -> !e.equals(entity)).map(e -> e.getAnimation()).toList());

                        FrameAnimation a = entity.getAnimation();

                        //TODO: make the position with an ratio
                        gameScreen.getGraphicsContext2D().drawImage(a.getByTime(time), a.getxLocation(), a.getyLocation(), a.getxSize(), a.getySize());
                    }
                }
                //endregion
            }
        }.start();
    }

    private void adjustScreenSize() {
        Window window = this.gameScreen.getScene().getWindow();
        window.setHeight(600);
        window.setWidth(600);

        this.gameScreen.setWidth(window.getWidth());
        this.gameScreen.setHeight(window.getHeight());

        /*
        window.setWidth((window.getHeight()/6*5)/30*40);

        for (Entity entity : entityPriorityQueue) {
            entity.adjustSpeedTimeRatio(this.gameScreen.getScene());

            //TODO: need a percent in entity for that
            entity.getAnimation().setxSize(gameScreen.getWidth() / 100 * entity.getAnimation().getxSize());
            entity.getAnimation().setySize(gameScreen.getWidth() / 100 * entity.getAnimation().getySize());
        }*/
    }

    public void deleteAllEntities() {
        entityList.clear();
    }

    /**
     * Returns a List with your desired animation-pictures
     * or a list with size 0 when something went wrong
     * @param directoryName
     * @param fileName
     * @param isAnimation
     * @return List of Images
     */
    public static List<Image> getAnimationImages(String directoryName, String fileName, boolean isAnimation){
        List<Image> animation = new ArrayList<>();

        if (isAnimation) {
            int counter = 1;
            String path = (animationDirectory + "animation/" + directoryName + "/" + fileName.split("\\.")[0] + counter + "." + fileName.split("\\.")[1]);

            while (Files.exists(Path.of(path).toAbsolutePath())) {
                animation.add(new Image(path));
                counter++;
                path = (animationDirectory + "animation/" + directoryName + "/" + fileName.split("\\.")[0] + counter + "." + fileName.split("\\.")[1]);
            }
        } else {
            String path = (animationDirectory + directoryName + "/" + fileName);

            if (Files.exists(Path.of(path).toAbsolutePath())) {
                animation.add(new Image(path));
            }
        }

        return animation;
    }
    //endregion
}

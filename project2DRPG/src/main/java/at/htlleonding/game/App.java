package at.htlleonding.game;

import at.htlleonding.game.Controller.GameScreen;
import at.htlleonding.game.Controller.InputController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setFullScreen(true);
        Group root = new Group();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("2D RPG");
        stage.show();

        InputController inputController = new InputController(scene);
        GameScreen gameScreen = new GameScreen(root);
        gameScreen.testMethod();


        String directoryName = "slime";
        String fileName = "slimeJump";
        String fileEnding = ".png";
        List<Image> slimeAnimation = gameScreen.getAnimationImages(directoryName, fileName, fileEnding);

        for (Image image : slimeAnimation) {
            
        }

        /*Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        int counter = 1;
        String directoryName = "slime";
        String fileName = "slimeJump";
        String fileEnding = ".png";
        List<Image> slimeAnimation = new ArrayList<>();

        while (Files.exists(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath())) {
            slimeAnimation.add(new Image(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath().toString()));
            counter++;
        }

        counter = 1;
        directoryName = "background";
        fileName = "background";
        fileEnding = ".png";
        List<Image> slimeBackgroundAnimation = new ArrayList<>();

        while (Files.exists(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath())) {
            slimeBackgroundAnimation.add(new Image(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath().toString()));
            counter++;
        }

        final long startNanoTime = System.nanoTime();
        final long durationSlimeJump = 130;
        final long durationBackground = 1100;

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                canvas.setHeight(stage.getHeight());
                canvas.setWidth(stage.getWidth());

                gc.clearRect(0, 0, 512, 512);
                final long time = (currentNanoTime - startNanoTime)/1000000;

                Image background = slimeBackgroundAnimation.get((int) ((time % (slimeBackgroundAnimation.size() * durationBackground)) / durationBackground));
                gc.drawImage(background, 0, 0, stage.getWidth(), stage.getWidth());

                Image image = slimeAnimation.get((int) ((time % (slimeAnimation.size() * durationSlimeJump)) / durationSlimeJump));
                // gc.drawImage(image, locX, locY, sizeX, sizeY);
                gc.drawImage(image, 10, 10, 160, 160);

                if (canvas.getHeight() > 600) {
                    this.stop();
                }
            }
        }.start();

        stage.show();
        stage.setTitle("2D RPG");
        stage.getIcons().add(new Image(Path.of("img/animation/slime/slimeJump1.png").toAbsolutePath().toString()));
        stage.show();*/
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();

        /*System.out.println("= Opening connection. =");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:db");
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int generatedId = -1;
        System.out.println("= Add a contact: =");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CONTACT (NAME, PHONENR, EMAIL) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Peter Bauer");
            preparedStatement.setString(2, "0732-6733680");
            preparedStatement.setString(3, "p.bauer@htl-leonding.ac.at");

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                generatedId = resultSet.getInt(1);
            }
            preparedStatement.close();

            System.out.printf("%d rows affected, created contact with id %d.%n", rowsAffected, generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("= Load and print all contacts: =");
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM CONTACT");
            while(resultSet.next()) {
                System.out.printf("%d,%s,%s,%s%n", resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("PHONENR"), resultSet.getString("EMAIL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("= Update a contact: =");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CONTACT SET EMAIL = ? WHERE ID = ?");
            preparedStatement.setString(1, "av@htl-leonding.ac.at");
            preparedStatement.setInt(2, generatedId);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.printf("%d rows affected.%n", rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("= Print all HTL Leonding contacts: =");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CONTACT WHERE EMAIL LIKE ?");
            preparedStatement.setString(1, "%@htl-leonding.ac.at");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.printf("%d,%s,%s,%s%n", resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("PHONENR"), resultSet.getString("EMAIL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("= Deleting contact: =");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CONTACT WHERE ID = ?");
            preparedStatement.setInt(1, generatedId);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.printf("%d rows affected.%n", rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("= Closing connection. =");
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
        catch (Exception ex) {

        }*/
    }
}
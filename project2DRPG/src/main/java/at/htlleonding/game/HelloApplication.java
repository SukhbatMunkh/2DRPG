package at.htlleonding.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        int counter = 1;
        String directoryName = "slime";
        String fileName = "slimeJump";
        String fileEnding = ".jpg";
        List<Image> slimeAnimation = new ArrayList<>();

        while (Files.exists(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath())) {
            slimeAnimation.add(new Image(Path.of("img/animation/" + directoryName + "/" + fileName + counter + fileEnding).toAbsolutePath().toString()));
            counter++;
        }

        final long startNanoTime = System.nanoTime();
        final long duration = 130;

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                final long time = (long) (System.nanoTime() - startNanoTime)/1000000;
                gc.drawImage(slimeAnimation.get((int) ((time % (slimeAnimation.size() * duration)) / duration)), (int) (scene.getHeight() / 2), (int) (scene.getWidth() / 2));
            }
        }.start();

        stage.show();
        stage.setTitle("2D RPG");
        stage.show();
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
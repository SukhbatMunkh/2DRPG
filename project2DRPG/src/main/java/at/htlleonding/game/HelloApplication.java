package at.htlleonding.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
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
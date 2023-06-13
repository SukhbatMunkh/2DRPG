package at.htlleonding.dungeonsandportals.database;

import at.htlleonding.dungeonsandportals.Controller.GameScreen;
import at.htlleonding.dungeonsandportals.Model.Direction;
import at.htlleonding.dungeonsandportals.Model.Entity;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseController {

    private static String getConnectionString() throws IOException {
        final String propertiesFilePath = "src/main/resources/at/htlleonding/dungeonsandportals/game.properties";

        final String dbFilePath = Path.of("src/main/resources/at/htlleonding/dungeonsandportals/data/").toAbsolutePath().toString();

        final String dbNamePropertyName = "sqliteDBName";

        Properties properties = new Properties();

        properties.load(new FileInputStream(propertiesFilePath));
        return "jdbc:sqlite:" + dbFilePath + "\\" + properties.getProperty(dbNamePropertyName);
    }

    public static Entity getAllMobsForScene(int sceneID){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT M_id, M_name FROM Mob;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.close();

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getInt(3));
                System.out.println(rs.getInt(4));
                System.out.println(rs.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    public static Image getSceneBackground(int sceneID) {
        Connection connection = null;
        Image image = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT I_imgName FROM Image INNER JOIN Scene S on Image.I_id = S.S_I_img WHERE S_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sceneID);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            image = GameScreen.getAnimationImages("background", rs.getString(1), false).get(0);

            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return image;
    }

    public static List<Image> getPlayerImages(Direction direction) {
        String playerImgDirectionName = "playerGoes";
        if (direction == Direction.WEST) {
            playerImgDirectionName += "Left.png";
        } else {
            playerImgDirectionName += "Right.png";
        }

        Connection connection = null;
        List<Image> images = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT I_directoryName, I_imgName FROM Image WHERE I_imgName like ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playerImgDirectionName);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            images = GameScreen.getAnimationImages(rs.getString(1), rs.getString(2), true);

            preparedStatement.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return images;
    }
}

package at.htlleonding.dungeonsandportals.database;

import at.htlleonding.dungeonsandportals.Model.Entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
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

    public static Entity getAllMobsForScene(int sceneId){
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

    public static String getSceneBackground(int sceneID) {
        Connection connection = null;
        String image = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT I_imgName FROM Image INNER JOIN Scene S on Image.I_id = S.S_I_img WHERE S_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sceneID);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            image = rs.getString(1);

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
}

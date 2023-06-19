package at.htlleonding.dungeonsandportals.database;

import at.htlleonding.dungeonsandportals.Controller.GameScreen;
import at.htlleonding.dungeonsandportals.Model.Direction;
import at.htlleonding.dungeonsandportals.Model.Entity;
import at.htlleonding.dungeonsandportals.Model.Player;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
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

    public static int getPlayerStartSceneID(int playerID) {
        Connection connection = null;
        int startSceneID = 0;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT Pl_S_sceneLoc FROM Player WHERE Pl_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playerID);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            startSceneID = rs.getInt(1);

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

        return startSceneID;
    }

    public static Player createNewPlayer(String playerName){
        int baseHealth = 3;
        int baseMana = 5;
        int baseKilledMobs = 0;
        int baseScene = 0;

        Connection connection = null;
        Player player = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "INSERT INTO Player (Pl_name, Pl_health, Pl_manaCapacity, Pl_killedMobs, Pl_S_sceneLoc) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, baseHealth);
            preparedStatement.setInt(3, baseMana);
            preparedStatement.setInt(4, baseKilledMobs);
            preparedStatement.setInt(5, baseScene);

            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Insertion of the new player failed, no rows affected");
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    player = new Player(keys.getInt(1), playerName, baseHealth, baseMana, baseKilledMobs);
                } else {
                    throw new SQLException("Insertion of the new player failed, no ID obtained");
                }
            }

            preparedStatement.close();
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

        return player;
    }

    public static List<Player> getPlayers() {
        Connection connection = null;
        List<Player> players = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT PL_ID, PL_NAME, PL_HEALTH, PL_MANACAPACITY, PL_KILLEDMOBS FROM Player";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Player player = new Player(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));

                players.add(player);
            }

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

        return players;
    }

    public static boolean removePlayerByID(int playerID) {
        Connection connection = null;
        boolean removedPlayer = true;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "DELETE FROM Player WHERE Pl_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playerID);

            if (preparedStatement.executeUpdate() == 0) {
                removedPlayer = false;
            }

            preparedStatement.close();
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

        return removedPlayer;
    }

    public static void savePlayer(Player player, int sceneID) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "UPDATE Player SET Pl_name=?, Pl_health=?, Pl_manaCapacity=?, Pl_killedMobs=?, Pl_S_sceneLoc=? WHERE Pl_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getName());
            preparedStatement.setInt(2, player.getHealth());
            preparedStatement.setInt(3, player.getMana());
            preparedStatement.setInt(4, player.getKilledMobs());
            preparedStatement.setInt(5, sceneID);
            preparedStatement.setInt(6, player.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
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
    }

    /**
     * returns the next scene
     * @param direction
     * @param curScene
     * @return -1 if one can't go there or the new scene
     */
    public static int getSceneViaDirection(Direction direction, int curScene) {
        Connection connection = null;
        int newScene = -1;

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT P_S_to FROM Paths WHERE P_S_from=? AND P_directionX=? AND P_directionY=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            int[] directionAsArray = Direction.getDirectionAsArray(direction);

            preparedStatement.setInt(1, curScene);
            preparedStatement.setInt(2, directionAsArray[0]);
            preparedStatement.setInt(3, directionAsArray[1]);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                newScene = rs.getInt(1);
            }

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

        return newScene;
    }

    public static List<Direction> getPathsAsDirection(int currentSceneID) {
        Connection connection = null;
        List<Direction> directions = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(getConnectionString());
            String sql = "SELECT P_directionX, P_directionY FROM Paths WHERE P_S_from=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentSceneID);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int[] directionArray = new int[2];
                directionArray[0] = rs.getInt(1);
                directionArray[1] = rs.getInt(2);

                directions.add(Direction.getDirectionFromArray(directionArray));
            }

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

        return directions;
    }
}

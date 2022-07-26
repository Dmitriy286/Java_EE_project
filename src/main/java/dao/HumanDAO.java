package dao;

import entity.Human;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HumanDAO {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "admin";

    //todo сделать ли connection статическим? Ведь надо же его закрывать.
    //todo инициализировать в статическом блоке?

    public Connection getNewConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("---!!!Здесь загружается драйвер!!!---");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    public List<Human> getAllHumans() {
        Connection connection = null;
        Statement statement = null;
        List<Human> humans = new ArrayList<>();
        try {
            connection = getNewConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM human");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                int money = resultSet.getInt("money");
                Human human = new Human(id, name, money);
                humans.add(human);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return humans;
    }

    public Human findById(int searchId) {
        Human human = null;
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM human WHERE id = ?;";
        try {
            connection = getNewConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, searchId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString("name");
            int money = resultSet.getInt("money");
            human = new Human(id, name, money);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return human;
    }

    public void insertNewHuman(Human human) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO human (name) VALUES (?)";
        try {
            connection = getNewConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, human.getName());

            statement.execute();
            System.out.println("Добавляется новая строка");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

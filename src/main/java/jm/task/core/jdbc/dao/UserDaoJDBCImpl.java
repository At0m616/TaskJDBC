package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, Name VARCHAR(20), LastName VARCHAR(20), age INT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        PreparedStatement statement = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES(?,?,?);");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
            System.out.println(" User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            Util.rollBack(connection);
            e.printStackTrace();
        } finally {
            Util.closeConnection(connection);
            Util.closeConnection(statement);
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        PreparedStatement statement = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("DELETE FROM users WHERE ID = ?;");
            statement.setLong(1, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            Util.rollBack(connection);
            e.printStackTrace();
        } finally {
            Util.closeConnection(connection);
            Util.closeConnection(statement);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT id, name, lastname, age FROM users;");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                result.add(user);
                connection.commit();
            }
        } catch (SQLException e) {
            Util.rollBack(connection);
            e.printStackTrace();
        } finally {
            Util.closeConnection(connection);
        }
        return result;
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

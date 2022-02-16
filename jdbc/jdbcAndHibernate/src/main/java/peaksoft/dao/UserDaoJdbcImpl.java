package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users (" +
        "id serial," +
        "name varchar(100)," +
        "lastName varchar(100)," +
        "age integer)";
        Connection connect = null;
        try {
            connect = Util.jdbcConnect();
            PreparedStatement preparedStatement = connect.prepareStatement(SQL);
            preparedStatement.executeUpdate();
            System.out.println("Таблица успешно создана");
            System.out.println("");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Connection connect = Util.jdbcConnect()) {
            PreparedStatement prepStat = connect.prepareStatement("DROP TABLE IF EXISTS users");
            prepStat.executeUpdate();
            System.out.println("Таблица успешно удалена\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users(name, lastname, age) values(?, ?, ?)";
        try(Connection connection = Util.jdbcConnect()) {
            PreparedStatement prepStatement = connection.prepareStatement(SQL);
            prepStatement.setString(1, name);
            prepStatement.setString(2, lastName);
            prepStatement.setByte(3, age);
            prepStatement.executeUpdate();
            System.out.println("Пользователь " + name +  " успешно добавлен в базу\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Connection connect = Util.jdbcConnect()) {
            PreparedStatement prepStat = connect.prepareStatement("DELETE FROM users WHERE id = ?");
            prepStat.setLong(1, id);
            prepStat.executeUpdate();
            System.out.println("пользователь с id " + id + " успешно удалён\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        String SQL = "SELECT * FROM users";

        List<User> userList = new ArrayList<>();

        try(Connection connect = Util.jdbcConnect()) {
            Statement statement = connect.createStatement();
            ResultSet resultSet =statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Connection connect = Util.jdbcConnect()) {
            PreparedStatement prepStat = connect.prepareStatement("TRUNCATE TABLE users");
            prepStat.executeUpdate();
            System.out.println("Таблицы очищена\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
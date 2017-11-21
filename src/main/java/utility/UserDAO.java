package utility;

import model.Task;
import model.User;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    public void addUser(User user) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO USER(username) values(?)");
            statement.setString(1, user.getUserName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("DELETE from User  where id=?");
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("UPDATE USER set userName=? where id=?");
            statement.setString(1, user.getUserName());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int userId) {
        Connection conn = null;
        DataSource dataSource;
        User user = null;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from USER where id=?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsers() {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        List<User> users = new ArrayList<>();
        User user= null;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from user");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return users;
    }

}

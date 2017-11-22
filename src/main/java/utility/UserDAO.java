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
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO USER(username,email,phone,location) values(?,?,?,?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getLocation());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(int userId) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("DELETE from User  where userId=?");
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUser(User user) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("UPDATE USER set userName=?,email=?,phone=?,location=? where userId=?");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getLocation());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User getUser(int userId) {
        Connection conn = null;
        DataSource dataSource;
        User user = null;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from USER where userId=?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setLocation(rs.getString("location"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from user");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setLocation(rs.getString("location"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

}

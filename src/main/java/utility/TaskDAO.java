package utility;

import model.Task;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskDAO {
    //@Resource(name = "jdbc/UsersDB")
    // private DataSource dataSource;

    public void addTask(Task task) {
        Connection conn = null;
        DataSource dataSource;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parsed = format.parse(task.getDueDate());
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO TASKS(name, dueDate, category, userId, priority, status) values(?,?,?,?,?,?)");
            statement.setString(1, task.getTask());
            statement.setDate(2, new java.sql.Date(parsed.getTime()));
            statement.setString(3, task.getCategory());
            statement.setInt(4, task.getId());
            statement.setInt(5, task.getPriority());
            statement.setBoolean(6, task.isComplete());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void deleteTask(int taskid) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("DELETE from TASKS  where id=?");
            statement.setInt(1, taskid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parsed = format.parse(task.getDueDate());

            PreparedStatement statement = conn.prepareStatement("UPDATE TASKS set name=?, dueDate=?, category=?, userId=?, priority=?, status=? where id=?");
            statement.setString(1, task.getTask());
            statement.setDate(2, new java.sql.Date(parsed.getTime()));
            statement.setString(3, task.getCategory());
            statement.setInt(4, task.getId());
            statement.setInt(5, task.getPriority());
            statement.setBoolean(6, task.isComplete());
            statement.setInt(7, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void completeTask(int taskId) {
        Connection conn = null;
        DataSource dataSource;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("UPDATE TASKS set status=? where id=?");
            statement.setBoolean(1, true);
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Task getTask(int taskid) {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from tasks where id=?");
            statement.setInt(1, taskid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                System.out.println(rs.getString("name"));
                task.setDueDate(rs.getString("dueDate"));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                task.setComplete(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return task;
    }

    public List<Task> getAllTask() {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from tasks");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                task.setDueDate(rs.getString("dueDate"));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                task.setComplete(rs.getBoolean("status"));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTeamTasks() {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            conn = dbConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from tasks where ");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                task.setDueDate(rs.getString("dueDate"));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}

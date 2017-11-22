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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(task.getRequiredBy());
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO TASKS(name, dueDate, category, userId, priority, status) values(?,?,?,?,?,?)");
            statement.setString(1, task.getTask());
            statement.setDate(2, new java.sql.Date(parsed.getTime()));
            statement.setString(3, task.getCategory());
            statement.setInt(4, task.getUserId());
            statement.setInt(5, task.getPriority());
            statement.setBoolean(6, task.isComplete());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void deleteTask(int taskid) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("DELETE from TASKS  where id=?");
            statement.setInt(1, taskid);
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

    public void updateTask(Task task) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(task.getRequiredBy());

            PreparedStatement statement = conn.prepareStatement("UPDATE TASKS set name=?, dueDate=?, category=?, userId=?, priority=?, status=? where id=?");
            statement.setString(1, task.getTask());
            statement.setDate(2, new java.sql.Date(parsed.getTime()));
            statement.setString(3, task.getCategory());
            statement.setInt(4, task.getUserId());
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
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void completeTask(int taskId) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("UPDATE TASKS set status=? where id=?");
            statement.setBoolean(1, true);
            statement.setInt(2, taskId);
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

    public Task getTask(int taskid) {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from tasks where id=?");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            statement.setInt(1, taskid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                task.setRequiredBy(format.format(rs.getDate("dueDate")));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                task.setComplete(rs.getBoolean("status"));
                task.setUserName(rs.getString("username"));
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

        return task;
    }

    public List<Task> getAllTask(String sortType) {
        Connection conn = null;
        DataSource dataSource;
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        try {

            conn = DBConnection.getCon();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            PreparedStatement statement = conn.prepareStatement("Select t.*, u.username  from tasks t, taskdb.user u where u.userid = t.userId order by ?");
            if(!("priority".equals(sortType) || "dueDate".equals(sortType)))
                sortType ="priority";
            statement.setString(1,sortType);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                task.setRequiredBy(format.format(rs.getDate("dueDate")));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                task.setComplete(rs.getBoolean("status"));
                task.setUserName(rs.getString("username"));
                tasks.add(task);
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

        return tasks;
    }

}

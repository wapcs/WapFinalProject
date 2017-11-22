package utility;

import model.Task;
import model.Team;
import model.TeamTask;
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

public class TeamDAO {
    public void addTeam(Team team) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO TEAM(teamName, description) values(?,?)");
            statement.setString(1, team.getTeamName());
            statement.setString(2, team.getDescription());
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

    public void deleteTeam(int teamId) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("DELETE from TEAM  where id=?");
            statement.setInt(1, teamId);
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

    public void updateTeam(Team team) {
        Connection conn = null;
        DataSource dataSource;
        try {
            conn = DBConnection.getCon();

            PreparedStatement statement = conn.prepareStatement("UPDATE TEAM set teamName=?, description=? where id=?");
            statement.setString(1, team.getTeamName());
            statement.setString(2, team.getDescription());
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

    public Team getTeam(int teamId) {
        Connection conn = null;
        DataSource dataSource;
        Team team = null;
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from Team where id=?");
            statement.setInt(1, teamId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                team = new Team();
                team.setId(rs.getInt("id"));
                team.setTeamName(rs.getString("teamName"));
                team.setDescription(rs.getString("description"));
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

        return team;
    }

    public List<Team> getAllTeam() {
        Connection conn = null;
        DataSource dataSource;
        Team team = null;
        List<Team> teams = new ArrayList<>();
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("Select * from Team");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                team = new Team();
                team.setId(rs.getInt("id"));
                team.setTeamName(rs.getString("teamName"));
                team.setDescription(rs.getString("description"));
                  teams.add(team);
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

        return teams;
    }

    public List<TeamTask> getTeamTasks(int teamId) {
        Connection conn = null;
        DataSource dataSource;
        TeamTask teamTask = null;
        List<TeamTask> teamTasks = new ArrayList<>();
        try {
            conn = DBConnection.getCon();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            PreparedStatement statement = conn.prepareStatement("  Select t.*, t.id taskId, tm.id teamId ,u.username , u.userId  from tasks t, team tm, taskdb.user u, teammember m where t.userId =u.userId and tm.id = m.teamId and u.userId=m.userId and tm.id=?");
            statement.setInt(1, teamId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                teamTask = new TeamTask();
                teamTask.setTaskId(rs.getInt("id"));
                teamTask.setTask(rs.getString("name"));
                teamTask.setRequiredBy(format.format(rs.getDate("dueDate")));
                teamTask.setCategory(rs.getString("category"));
                teamTask.setUserId(rs.getInt("userId"));
                teamTask.setPriority(rs.getInt("priority"));
                teamTask.setUsername(rs.getString("username"));
                teamTask.setTeamId(rs.getInt("teamId"));
                teamTasks.add(teamTask);
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

        return teamTasks;
    }

    public List<TeamTask> getUserTasks(int userId) {
        Connection conn = null;
        DataSource dataSource;
        TeamTask task = null;
        List<TeamTask> userTasks = new ArrayList<>();
        try {
            conn = DBConnection.getCon();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            PreparedStatement statement = conn.prepareStatement("Select t.*, t.id taskId ,u.username , u.userId  from tasks t, taskdb.user u where t.userId =u.userId and u.userid=?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                task = new TeamTask();
                task.setTaskId(rs.getInt("id"));
                task.setTask(rs.getString("name"));
                task.setRequiredBy(format.format(rs.getDate("dueDate")));
                task.setCategory(rs.getString("category"));
                task.setUserId(rs.getInt("userId"));
                task.setPriority(rs.getInt("priority"));
                task.setComplete(rs.getBoolean("status"));
                task.setUsername(rs.getString("username"));
                userTasks.add(task);
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

        return userTasks;
    }

    public List<User> getTeamMembers(int teamId) {
        Connection conn = null;
        DataSource dataSource;
        User user = null;
        List<User> teamMembers = new ArrayList<>();
        try {
            conn = DBConnection.getCon();
            PreparedStatement statement = conn.prepareStatement("  Select u.*  from  team tm, taskdb.user u, teammember m where tm.id = m.teamId and u.userId=m.userId and tm.id=?");
            statement.setInt(1, teamId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                teamMembers.add(user);
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

        return teamMembers;
    }
}

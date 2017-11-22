package controller;

import com.google.gson.Gson;
import model.Task;
import model.Team;
import model.TeamTask;
import model.User;
import utility.TaskDAO;
import utility.TeamDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getMethod Called");
        PrintWriter out = response.getWriter();
        TeamDAO teamDAO = new TeamDAO();
        List<Team> teamList = null;
        List<TeamTask> teamTasks = null;
        List<User> users = null;
        String JSONtasks;
        String reqType = request.getParameter("method");
        String teamId = request.getParameter("teamId");
        String userId = request.getParameter("userId");
        String sortType="";
        if(request.getParameter("sortType")!=null)
        {
            sortType = request.getParameter("sortType");
        }
        System.out.println("method:"+request.getParameter("method"));
        if ("add".equals(reqType)) {
            Team team = new Team();
            team.setTeamName(request.getParameter("teamName"));
            team.setDescription(request.getParameter("description"));
            teamDAO.addTeam(team);
            teamList = teamDAO.getAllTeam();
            JSONtasks = new Gson().toJson(teamList);
        } else if ("edit".equals(reqType)) {
            Team team = new Team();
            team.setId(Integer.parseInt(request.getParameter("id")));
            team.setTeamName(request.getParameter("teamName"));
            team.setDescription(request.getParameter("description"));
            teamDAO.updateTeam(team);
            teamList = teamDAO.getAllTeam();
            JSONtasks = new Gson().toJson(teamList);
        } else if ("delete".equals(reqType)) {
            teamDAO.deleteTeam(Integer.parseInt(request.getParameter("id")));
            teamList = teamDAO.getAllTeam();
            JSONtasks = new Gson().toJson(teamList);
        } else if ("teamTask".equals(reqType)) {
            teamTasks = teamDAO.getTeamTasks(Integer.parseInt(request.getParameter("id")));
            //System.out.println("teamSIZE:"+teamList.size());
            JSONtasks = new Gson().toJson(teamTasks);
        }else if ("users".equals(reqType)) {
            users = teamDAO.getTeamMembers(Integer.parseInt(request.getParameter("id")));
            JSONtasks = new Gson().toJson(users);
        } else if ("userTasks".equals(reqType)) {
            teamTasks = teamDAO.getUserTasks(Integer.parseInt(request.getParameter("id")));
            JSONtasks = new Gson().toJson(teamTasks);
        }else if ("addMember".equals(reqType)) {
            teamDAO.addTeamMember(Integer.parseInt(request.getParameter("teamId")),Integer.parseInt(request.getParameter("userId")));
            teamList = teamDAO.getAllTeam();
            JSONtasks = new Gson().toJson(teamList);
        } else {
            System.out.println("sortType:"+sortType);
            if (!"".equals(sortType)) {
                if("".equals(userId)){
                    teamTasks =teamDAO.getTeamTasks(Integer.parseInt(teamId));
                    if("priority".equals(sortType)){
                        teamTasks.sort(Comparator.comparing(TeamTask::getPriority));
                    }
                    else{
                        teamTasks.sort(Comparator.comparing(TeamTask::getRequiredBy));
                    }
                }
                else{
                    System.out.println("userID:"+userId+" teamID="+teamId);
                    teamTasks = teamDAO.getUserTasks(Integer.parseInt(userId));
                    if("priority".equals(sortType)){
                        teamTasks.sort(Comparator.comparing(TeamTask::getPriority));
                    }
                    else{
                        teamTasks.sort(Comparator.comparing(TeamTask::getRequiredBy));
                    }
                }
                JSONtasks = new Gson().toJson(teamTasks);
            }
            else{
                teamList = teamDAO.getAllTeam();
                JSONtasks = new Gson().toJson(teamList);
            }


        }

//        teamTasks = teamDAO.getTeamTasks(1);
//        teamTasks.forEach(x -> System.out.println(x.getTask()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }
}

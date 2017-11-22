package controller;

import com.google.gson.Gson;
import model.Task;
import model.Team;
import model.TeamTask;
import utility.TaskDAO;
import utility.TeamDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        Boolean b = false;
        String reqType = request.getParameter("method");
        if ("add".equals(reqType)) {
            Team team = new Team();
            team.setTeamName(request.getParameter("teamName"));
            team.setDescription(request.getParameter("description"));
            teamDAO.addTeam(team);
            teamList = teamDAO.getAllTeam();
        } else if ("edit".equals(reqType)) {
            Team team = new Team();
            team.setId(Integer.parseInt(request.getParameter("id")));
            team.setTeamName(request.getParameter("teamName"));
            team.setDescription(request.getParameter("description"));
            teamDAO.updateTeam(team);
            teamList = teamDAO.getAllTeam();
        } else if ("delete".equals(reqType)) {
            teamDAO.deleteTeam(Integer.parseInt(request.getParameter("id")));
            teamList = teamDAO.getAllTeam();
        } else if ("teamTask".equals(reqType)) {
            teamTasks = teamDAO.getTeamTasks(Integer.parseInt(request.getParameter("id")));
            b=true;
        } else {
            teamList = teamDAO.getAllTeam();
        }
        String JSONtasks;
        if(b){
            JSONtasks = new Gson().toJson(teamTasks);
        }else{
            JSONtasks = new Gson().toJson(teamList);
        }
        teamTasks = teamDAO.getTeamTasks(1);
        teamTasks.forEach(x->System.out.println(x.getTask()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }
}

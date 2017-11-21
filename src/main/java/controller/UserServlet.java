package controller;

import com.google.gson.Gson;
import model.Task;
import model.User;
import utility.TaskDAO;
import utility.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserDAO userDAO = new UserDAO();
        Integer id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUser(id);
        System.out.println("UserName:" + user.getUserName());
        String JSONtasks;
        JSONtasks = new Gson().toJson(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getMethod Called");
        PrintWriter out = response.getWriter();
        UserDAO tUserDAO  = new UserDAO();
        List<User> users = null;
        String reqType = request.getParameter("method");
        if ("add".equals(reqType)) {
            System.out.println("add method called");
            User user = new User();
            user.setUserName(request.getParameter("userName"));
            users = tUserDAO.getAllUsers();
        } else if ("edit".equals(reqType)) {
            User user = new User();
            user.setUserName(request.getParameter("userName"));
            user.setId(Integer.parseInt(request.getParameter("id")));
            tUserDAO.updateUser(user);
            users = tUserDAO.getAllUsers();
        } else if ("delete".equals(reqType)) {
            tUserDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
            users = tUserDAO.getAllUsers();
        } else {
            users = tUserDAO.getAllUsers();
        }

        String JSONtasks;
        JSONtasks = new Gson().toJson(users);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }
}

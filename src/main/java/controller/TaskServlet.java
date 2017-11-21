package controller;

import com.google.gson.Gson;
import model.Task;
import utility.MockData;
import utility.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PostMethod Called");
        PrintWriter out = response.getWriter();
        TaskDAO taskDAO = new TaskDAO();
        Integer id = Integer.parseInt(request.getParameter("id"));
        Task task = taskDAO.getTask(id);
        System.out.println("taskName:" + task.getTask());
        String JSONtasks;
        JSONtasks = new Gson().toJson(task);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getMethod Called");
        PrintWriter out = response.getWriter();
        TaskDAO taskDAO = new TaskDAO();
        List<Task> taskList = null;
        String JSONtasks;
        String reqType = request.getParameter("method");
        if ("add".equals(reqType)) {
            System.out.println("add method called");
            Task task = new Task();
            task.setTask(request.getParameter("task"));
            task.setRequredBy(request.getParameter("requiredBy"));
            task.setCategory(request.getParameter("category"));
            task.setPriority(Integer.parseInt(request.getParameter("priority")));
            task.setUserId(Integer.parseInt(request.getParameter("userId")));
            taskDAO.addTask(task);
            taskList = taskDAO.getAllTask();
            JSONtasks = new Gson().toJson(taskList);
        } else if ("edit".equals(reqType)) {
            Task task = new Task();
            task.setId(Integer.parseInt(request.getParameter("id")));
            task.setTask(request.getParameter("task"));
            task.setRequredBy(request.getParameter("requiredBy"));
            task.setCategory(request.getParameter("category"));
            task.setPriority(Integer.parseInt(request.getParameter("priority")));
            task.setUserId(Integer.parseInt(request.getParameter("userId")));
            taskDAO.updateTask(task);
            taskList = taskDAO.getAllTask();
            JSONtasks = new Gson().toJson(taskList);
        } else if ("delete".equals(reqType)) {
            taskDAO.deleteTask(Integer.parseInt(request.getParameter("id")));
            taskList = taskDAO.getAllTask();
            JSONtasks = new Gson().toJson(taskList);
        } else if ("complete".equals(reqType)) {
            taskDAO.completeTask(Integer.parseInt(request.getParameter("id")));
            taskList = taskDAO.getAllTask();
            JSONtasks = new Gson().toJson(taskList);
        } else if ("getTask".equals(reqType)) {
            Task task = taskDAO.getTask(Integer.parseInt(request.getParameter("id")));
            JSONtasks = new Gson().toJson(task);
        } else {
            taskList = taskDAO.getAllTask();
            JSONtasks = new Gson().toJson(taskList);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(JSONtasks);
    }
}

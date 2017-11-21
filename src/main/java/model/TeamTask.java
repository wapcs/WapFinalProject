package model;

public class TeamTask {
    private int taskId;
    private String task;
    private String requiredBy;
    private String category;
    private int userId;
    private int priority;
    private boolean complete;
    private String username;
    private int teamId;

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {

        return teamId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setRequiredBy(String dueDate) {
        this.requiredBy = dueDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTaskId() {

        return taskId;
    }

    public String getTask() {
        return task;
    }

    public String getRequiredBy() {
        return requiredBy;
    }

    public String getCategory() {
        return category;
    }

    public int getUserId() {
        return userId;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isComplete() {
        return complete;
    }

    public String getUsername() {
        return username;
    }
}

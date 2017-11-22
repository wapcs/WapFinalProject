package model;

public class Task {

    private int id;
    private String task;
    private String requiredBy;
    private String category;
    private int userId;
    private int priority;
    private boolean complete;
    private String userName;

    public Task() {
    }


    public Task(int id, String task, String dueDate, String category) {
        this.id = id;
        this.task = task;
        this.requiredBy = dueDate;
        this.category = category;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getRequiredBy() {
        return requiredBy;
    }

    public void setRequiredBy(String requredBy) {
        this.requiredBy = requredBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public int getUserId() {
        return userId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}

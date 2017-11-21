package model;

public class Team {
    private int id;
    private String teamName;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

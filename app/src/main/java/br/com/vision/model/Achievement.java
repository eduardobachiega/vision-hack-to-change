package br.com.vision.model;

public class Achievement {
    private String goal;
    private String prize;
    private String status;
    private boolean completed;

    public Achievement(boolean completed, String goal, String prize, String status) {
        this.completed = completed;
        this.goal = goal;
        this.prize = prize;
        this.status = status;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

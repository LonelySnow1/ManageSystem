package com.lonelySnow.pojo;


public class PersonTask {
    private Integer taskId;


    private String title;
    private String briefing;
    private String detail;

    private String startTime;
    private String finishTime;

    private String state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }


    @Override
    public String toString() {
        return "PersonTask{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", briefing='" + briefing + '\'' +
                ", detail='" + detail + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

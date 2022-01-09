package hu.unideb.inf.mathhelper.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String nickname;
    private final List<String> completedQuestionIds;
    private Integer level;
    private Integer xp;
    private Integer numberOfCompletedQuestions;
    private Integer helpPoints;

    public User(String nickname) {
        this.nickname = nickname;
        this.completedQuestionIds = new ArrayList<>();
        this.helpPoints = 0;
        this.level = 1;
        this.xp = 0;
        this.numberOfCompletedQuestions = 0;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addCompletedQuestionIds(String completedQuestionId) {
        this.completedQuestionIds.add(completedQuestionId);
    }

    public void incrementLevel() {
        this.level++;
    }

    public void incrementXp(Integer xp) {
        this.xp += xp;
    }

    public void incrementNumberOfCompletedQuestions() {
        this.numberOfCompletedQuestions++;
    }

    public void incrementHelpPoints(Integer helpPoints) {
        this.helpPoints += helpPoints;
    }

    public void decrementHelpPoints(Integer helpPoints) {
        this.helpPoints -= helpPoints;
    }

    public String getNickname() {
        return nickname;
    }

    public List<String> getCompletedQuestionIds() {
        return completedQuestionIds;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getXp() {
        return xp;
    }

    public Integer getNumberOfCompletedQuestions() {
        return numberOfCompletedQuestions;
    }

    public Integer getHelpPoints() {
        return helpPoints;
    }
}

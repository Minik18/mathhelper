package hu.unideb.inf.mathhelper.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String profilePictureName;

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private List<String> completedQuestionIds;
    private Integer countOfFinals;
    private Integer level;
    private Integer xp;
    private Integer numberOfCompletedQuestions;
    private Integer helpPoints;
    private Integer rewardPoints;

    public User(String nickname) {
        this.nickname = nickname;
        resetData();
    }

    public void resetData() {
        this.helpPoints = 0;
        this.level = 1;
        this.xp = 0;
        this.numberOfCompletedQuestions = 0;
        this.rewardPoints = 0;
        this.countOfFinals = 0;
        this.profilePictureName = "";
        completedQuestionIds = new ArrayList<>();
    }

    protected User() {
    }

    public void incrementCountOfFinals() {
        countOfFinals++;
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

    public void decrementXp(Integer xp) {
        this.xp -= xp;
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

    public void incrementRewardPoints(Integer rewardPoints) {
        this.rewardPoints += rewardPoints;
    }

    public void decrementRewardPoints(Integer rewardPoints) {
        this.rewardPoints -= rewardPoints;
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

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public Integer getCountOfFinals() {
        return countOfFinals;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void updateProfilePicture(String path) {
        this.profilePictureName = path;
    }
}

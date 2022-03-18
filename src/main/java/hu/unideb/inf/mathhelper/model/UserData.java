package hu.unideb.inf.mathhelper.model;

import java.util.List;

public class UserData {

    private String nickname;
    private List<String> completedQuestionIds;
    private Integer level;
    private Integer xp;
    private Integer numberOfCompletedQuestions;
    private Integer helpPoints;
    private Integer rewardPoints;
    private Integer countOfFinals;
    private String profilePictureName;
    private Integer studentKnowledgePoint;
    private Integer bossLevel;

    private UserData() {

    }

    public static class Builder {
        private String nickname;
        private List<String> completedQuestionIds;
        private Integer level;
        private Integer xp;
        private Integer numberOfCompletedQuestions;
        private Integer helpPoints;
        private Integer rewardPoints;
        private Integer countOfFinals;
        private String profilePictureName;
        private Integer studentKnowledgePoint;
        private Integer bossLevel;

        public Builder withBossLevel(Integer level) {
            this.bossLevel = level;
            return this;
        }

        public Builder withStudentKnowledgePoints(Integer points) {
            this.studentKnowledgePoint = points;
            return this;
        }

        public Builder withProfilePictureName(String name) {
            this.profilePictureName = name;
            return this;
        }

        public Builder withCountOfFinals(Integer number) {
            this.countOfFinals = number;
            return this;
        }

        public Builder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder withCompletedQuestionIds(List<String> list) {
            this.completedQuestionIds = list;
            return this;
        }

        public Builder withLevel(Integer level) {
            this.level = level;
            return this;
        }

        public Builder withXp(Integer xp) {
            this.xp = xp;
            return this;
        }

        public Builder withRewardPoints(Integer rewardPoints) {
            this.rewardPoints = rewardPoints;
            return this;
        }

        public Builder withNumberOfCompletedQuestions(Integer numberOfCompletedQuestions) {
            this.numberOfCompletedQuestions = numberOfCompletedQuestions;
            return this;
        }

        public Builder withHelpPoints(Integer helpPoints) {
            this.helpPoints = helpPoints;
            return this;
        }

        public UserData build() {
            UserData userData = new UserData();
            userData.xp = this.xp;
            userData.completedQuestionIds = this.completedQuestionIds;
            userData.helpPoints = this.helpPoints;
            userData.level = this.level;
            userData.nickname = this.nickname;
            userData.numberOfCompletedQuestions = this.numberOfCompletedQuestions;
            userData.rewardPoints = this.rewardPoints;
            userData.countOfFinals = this.countOfFinals;
            userData.profilePictureName = this.profilePictureName;
            userData.studentKnowledgePoint = this.studentKnowledgePoint;
            userData.bossLevel = this.bossLevel;
            return userData;
        }
    }

    public Integer getBossLevel() {
        return bossLevel;
    }

    public Integer getCountOfFinals() {
        return countOfFinals;
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

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public Integer getStudentKnowledgePoint() {
        return studentKnowledgePoint;
    }
}

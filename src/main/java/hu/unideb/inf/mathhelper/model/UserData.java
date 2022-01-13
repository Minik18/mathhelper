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
            return userData;
        }
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
}

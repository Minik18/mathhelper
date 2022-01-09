package hu.unideb.inf.mathhelper.model;

import java.util.List;

public class UserData {

    private String nickname;
    private List<String> completedQuestionIds;
    private Integer level;
    private Integer xp;
    private Integer numberOfCompletedQuestions;
    private Integer helpPoints;

    private UserData() {

    }

    public static class Builder {
        private String nickname;
        private List<String> completedQuestionIds;
        private Integer level;
        private Integer xp;
        private Integer numberOfCompletedQuestions;
        private Integer helpPoints;

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
            this.xp = userData.xp;
            this.level = userData.level;
            this.completedQuestionIds = userData.completedQuestionIds;
            this.numberOfCompletedQuestions = userData.numberOfCompletedQuestions;
            this.nickname = userData.nickname;
            this.helpPoints = userData.helpPoints;
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
}

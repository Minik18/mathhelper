package hu.unideb.inf.mathhelper.model;

public class Location {

    private String levelsFile;
    private String questionFolder;
    private String picturesFolder;
    private String scenesFolder;
    private String panesFolder;
    private String textFolder;
    private String uiPicturesFolder;
    private String categoryFile;
    private String sampleQuestionFile;
    private String defaultSettingsFile;
    private String settingsFile;
    private String profilePictureFolder;
    private String bossesFile;

    private Location() {
    }

    public static class Builder {
        private String levelsFile;
        private String questionFolder;
        private String picturesFolder;
        private String scenesFolder;
        private String panesFolder;
        private String textFolder;
        private String uiPicturesFolder;
        private String categoryFile;
        private String sampleQuestionFile;
        private String defaultSettingsFile;
        private String settingsFile;
        private String profilePictureFolder;
        private String bossesFile;

        public Builder withBossesFile(String bossFilePath) {
            this.bossesFile = bossFilePath;
            return this;
        }

        public Builder withProfilePictureFolder(String profilePictureFolder) {
            this.profilePictureFolder = profilePictureFolder;
            return this;
        }

        public Builder withDefaultSettingsFile(String defaultSettingsFile) {
            this.defaultSettingsFile = defaultSettingsFile;
            return this;
        }

        public Builder withSettingsFile(String settingsFile) {
            this.settingsFile = settingsFile;
            return this;
        }

        public Builder withSampleQuestionFile(String sampleQuestionFile) {
            this.sampleQuestionFile = sampleQuestionFile;
            return this;
        }

        public Builder withCategoryFilePath(String categoryFile) {
            this.categoryFile = categoryFile;
            return this;
        }

        public Builder withUiPicturesFolder(String uiPicturesFolder) {
            this.uiPicturesFolder = uiPicturesFolder;
            return this;
        }

        public Builder withLevelsFile(String levelsFile) {
            this.levelsFile = levelsFile;
            return this;
        }

        public Builder withQuestionFolder(String questionFolder) {
            this.questionFolder = questionFolder;
            return this;
        }

        public Builder withTextFolder(String textFolder) {
            this.textFolder = textFolder;
            return this;
        }

        public Builder withScenesFolder(String scenesFolder) {
            this.scenesFolder = scenesFolder;
            return this;
        }

        public Builder withPanesFolder(String panesFolder) {
            this.panesFolder = panesFolder;
            return this;
        }

        public Builder withPicturesFolder(String picturesFolder) {
            this.picturesFolder = picturesFolder;
            return this;
        }

        public Location build() {
            Location location = new Location();
            location.levelsFile = this.levelsFile;
            location.picturesFolder = this.picturesFolder;
            location.questionFolder = this.questionFolder;
            location.panesFolder = this.panesFolder;
            location.scenesFolder = this.scenesFolder;
            location.textFolder = this.textFolder;
            location.uiPicturesFolder = this.uiPicturesFolder;
            location.categoryFile = this.categoryFile;
            location.sampleQuestionFile = this.sampleQuestionFile;
            location.settingsFile = this.settingsFile;
            location.defaultSettingsFile = this.defaultSettingsFile;
            location.profilePictureFolder = this.profilePictureFolder;
            location.bossesFile = this.bossesFile;
            return location;
        }
    }

    public String getBossesFile() {
        return bossesFile;
    }

    public String getCategoryFile() {
        return categoryFile;
    }

    public String getSampleQuestionFile() {
        return sampleQuestionFile;
    }

    public String getUiPicturesFolder() {
        return uiPicturesFolder;
    }

    public String getScenesFolder() {
        return scenesFolder;
    }

    public String getPanesFolder() {
        return panesFolder;
    }

    public String getLevelsFile() {
        return levelsFile;
    }

    public String getQuestionFolder() {
        return questionFolder;
    }

    public String getPicturesFolder() {
        return picturesFolder;
    }

    public String getTextFolder() {
        return textFolder;
    }

    public String getDefaultSettingsFile() {
        return defaultSettingsFile;
    }

    public String getSettingsFile() {
        return settingsFile;
    }

    public String getProfilePictureFolder() {
        return profilePictureFolder;
    }
}

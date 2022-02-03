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

    private Location() {}

    public static class Builder {
        private String levelsFile;
        private String questionFolder;
        private String picturesFolder;
        private String scenesFolder;
        private String panesFolder;
        private String textFolder;
        private String uiPicturesFolder;
        private String categoryFile;

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
            return location;
        }
    }

    public String getCategoryFile() {
        return categoryFile;
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
}

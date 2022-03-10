package hu.unideb.inf.mathhelper.model;

public class Settings {

    private boolean useSolvedQuestions;

    public boolean isUseSolvedQuestions() {
        return useSolvedQuestions;
    }

    private Settings() {}

    public static class Builder {

        private boolean useSolvedQuestions;

        public Builder setUseSolvedQuestions(boolean useSolvedQuestions) {
            this.useSolvedQuestions = useSolvedQuestions;
            return this;
        }

        public Settings build() {

            Settings settings = new Settings();

            settings.useSolvedQuestions = useSolvedQuestions;

            return settings;

        }

    }

}

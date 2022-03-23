package hu.unideb.inf.mathhelper.model;

public class Settings {

    private boolean useSolvedQuestions;
    private boolean firstBossFightOpen;
    private boolean allBossesDefeated;

    public boolean isUseSolvedQuestions() {
        return useSolvedQuestions;
    }

    public boolean isFirstBossFightOpen() {
        return firstBossFightOpen;
    }

    public boolean isAllBossesDefeated() {
        return allBossesDefeated;
    }

    private Settings() {
    }

    public static class Builder {

        private boolean useSolvedQuestions;
        private boolean firstBossFightOpen;
        private boolean allBossesDefeated;

        public Builder setFirstBossFightOpen(boolean firstBossFightOpen) {
            this.firstBossFightOpen = firstBossFightOpen;
            return this;
        }

        public Builder setAllBossesDefeated(boolean allBossesDefeated) {
            this.allBossesDefeated = allBossesDefeated;
            return this;
        }

        public Builder setUseSolvedQuestions(boolean useSolvedQuestions) {
            this.useSolvedQuestions = useSolvedQuestions;
            return this;
        }

        public Settings build() {

            Settings settings = new Settings();

            settings.useSolvedQuestions = useSolvedQuestions;
            settings.allBossesDefeated = allBossesDefeated;
            settings.firstBossFightOpen = firstBossFightOpen;

            return settings;

        }

    }

}

package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.model.Settings;

public interface SettingsDAO {

    Settings getSettings();

    void changeShowSolvedQuestions(boolean value);

    void changeFirstOpenBossFight(boolean value);

    void changeAllBossesDefeated(boolean value);

}

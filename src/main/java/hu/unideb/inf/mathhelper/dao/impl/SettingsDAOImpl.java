package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SettingsDAO;
import hu.unideb.inf.mathhelper.model.Settings;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Properties;

@Component
public class SettingsDAOImpl implements SettingsDAO {

    private final LocationDAO locationDAO;
    private final Properties properties;
    private File settingsFile;

    private Settings settings;

    @Autowired
    public SettingsDAOImpl(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
        properties = new Properties();
        settingsFile = new File(locationDAO.getSettingsFilePath());
    }

    @Override
    public Settings getSettings() {
        load();
        return settings;
    }

    @Override
    public void changeShowSolvedQuestions(boolean value) {
        properties.setProperty("show_solved_questions",String.valueOf(value));
        updateFile();
    }

    @Override
    public void changeFirstOpenBossFight(boolean value) {
        properties.setProperty("first_boss_fight_open",String.valueOf(value));
        updateFile();
    }

    @Override
    public void changeAllBossesDefeated(boolean value) {
        properties.setProperty("all_bosses_defeated",String.valueOf(value));
        updateFile();
    }

    private void updateFile() {
        try {
            properties.store(new FileWriter(settingsFile),null);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    private void load() {

        if (!settingsFile.exists()) {
            try {
                URL inputUrl = new URL(locationDAO.getDefaultSettingsFilePath());
                File dest = new File(locationDAO.getSettingsFilePath());
                FileUtils.copyURLToFile(inputUrl, dest);
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
            settingsFile = new File(locationDAO.getSettingsFilePath());
        }

        try {
            properties.load(new FileInputStream(settingsFile));
            this.settings = new Settings.Builder()
                    .setUseSolvedQuestions(Boolean.parseBoolean(properties.getProperty("show_solved_questions")))
                    .setFirstBossFightOpen(Boolean.parseBoolean(properties.getProperty("first_boss_fight_open")))
                    .setAllBossesDefeated(Boolean.parseBoolean(properties.getProperty("all_bosses_defeated")))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

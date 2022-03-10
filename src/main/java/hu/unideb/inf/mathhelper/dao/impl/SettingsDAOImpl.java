package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SettingsDAO;
import hu.unideb.inf.mathhelper.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        if (settings == null) {
            load();
        }
        return settings;
    }

    @Override
    public void changeShowSolvedQuestions(boolean value) {
        properties.setProperty("show_solved_questions",String.valueOf(value));
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
            File defaultSettingsFile = new File(locationDAO.getDefaultSettingsFilePath().getPath());
            try {
                Files.copy(Path.of(defaultSettingsFile.getPath()), Path.of(settingsFile.getPath()));
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
            settingsFile = new File(locationDAO.getSettingsFilePath());
        }

        try {
            properties.load(new FileInputStream(settingsFile));
            this.settings = new Settings.Builder()
                    .setUseSolvedQuestions(Boolean.getBoolean(properties.getProperty("show_solved_questions")))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

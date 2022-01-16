package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.Location;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import java.util.Objects;

public class LocationDAOImpl implements LocationDAO {

    private static final String LOCATION_FILE_PATH = Objects.requireNonNull(LocationDAOImpl.class.
            getClassLoader().getResource("settings/locations.properties")).getPath();

    private Location location;

    @Override
    public String getQuestionsFolderPath() {
        if (location == null) {
            load();
        }
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(location.getQuestionFolder())).getPath();
    }

    @Override
    public String getPicturesFolderPath() {
        if (location == null) {
            load();
        }
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(location.getPicturesFolder())).getPath();
    }

    @Override
    public String getLevelSystemFilePath() {
        if (location == null) {
            load();
        }
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(location.getLevelsFile())).getPath();
    }

    @Override
    public String getScenesFolderPath() {
        if (location == null) {
            load();
        }
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(location.getScenesFolder())).getPath();
    }

    @Override
    public String getPanesFolderPath() {
        if (location == null) {
            load();
        }
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(location.getPanesFolder())).getPath();
    }

    @Override
    public String getTextFolderPath() {
        if (location == null) {
            load();
        }
        return location.getTextFolder();
    }

    private void load() {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration(LOCATION_FILE_PATH));
            location = new Location.Builder()
                    .withLevelsFile(config.getString("location.level"))
                    .withPicturesFolder(config.getString("location.pictures_folder"))
                    .withQuestionFolder(config.getString("location.questions_folder"))
                    .withPanesFolder(config.getString("location.ui.panes_folder"))
                    .withScenesFolder(config.getString("location.ui.scenes_folder"))
                    .withTextFolder(config.getString("location.ui.text_folder"))
                    .build();
        } catch (ConfigurationException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
    }

}

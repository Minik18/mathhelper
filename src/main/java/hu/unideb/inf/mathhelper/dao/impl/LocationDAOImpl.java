package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.Location;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import java.net.URL;
import java.util.Objects;

public class LocationDAOImpl implements LocationDAO {

    private static final String LOCATION_FILE_PATH = Objects.requireNonNull(LocationDAOImpl.class.
            getClassLoader().getResource("settings/locations.properties")).getPath();

    private Location location;

    @Override
    public URL getQuestionFolderPath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getQuestionFolder());
    }

    @Override
    public String getQuestionPictureFilePath(String fileName) {
        if (location == null) {
            load();
        }
        String folderName = fileName.substring(0,fileName.indexOf("_",5));
        return getURLFromPath(location.getPicturesFolder() + "/" + folderName + "/" + fileName).toString();
    }

    @Override
    public String getUiPictureFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getUiPicturesFolder() + "/" + fileName).toString();
    }

    @Override
    public URL getLevelSystemFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getLevelsFile());
    }

    @Override
    public URL getSceneFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getScenesFolder() + "/" + fileName);
    }

    @Override
    public URL getPaneFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getPanesFolder() + "/" + fileName);
    }

    @Override
    public String getTextFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return location.getTextFolder() + "/" + fileName;
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
                    .withUiPicturesFolder(config.getString("location.ui.pictures_folder"))
                    .build();
        } catch (ConfigurationException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
    }

    private URL getURLFromPath(String path) {
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(path));
    }

}

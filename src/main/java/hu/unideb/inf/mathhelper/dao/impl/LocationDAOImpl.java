package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.Location;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.Objects;

public class LocationDAOImpl implements LocationDAO {

    private static final String LOCATION_FILE_PATH = Objects.requireNonNull(LocationDAOImpl.class.
            getClassLoader().getResource("settings/locations.xml")).getPath();

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

    private void load() {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        try {
            jaxbContext = JAXBContext.newInstance(Location.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(LOCATION_FILE_PATH);
            this.location = (Location) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            //TODO: Log error
            e.printStackTrace();
        }
    }

}

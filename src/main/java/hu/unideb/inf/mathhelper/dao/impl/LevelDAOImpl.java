package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.model.level.Levels;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.util.*;

public class LevelDAOImpl implements LevelDAO {

    private List<Level> levels;
    private final LocationDAO locationDAO;

    public LevelDAOImpl(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }


    @Override
    public List<Level> getLevelSystem() {
        if (levels == null) {
            loadLevel();
        }
        return levels;
    }

    private void loadLevel() {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        List<Level> result = new ArrayList<>();
        try {
            jaxbContext = JAXBContext.newInstance(Levels.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            Levels levels = (Levels) unmarshaller.unmarshal(locationDAO.getLevelSystemFilePath());
            result.addAll(levels.getLevelList());
        } catch (JAXBException e) {
            AppLogger.logError(e);
        }
        levels = result;
    }
}

package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.model.level.Levels;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.net.URL;
import java.util.*;

public class LevelDAOImpl implements LevelDAO {

    private List<Level> levels;

    @Override
    public List<Level> getLevelSystem(URL path) {
        if (levels == null) {
            loadLevel(path);
        }
        return levels;
    }
    private void loadLevel(URL path) {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        List<Level> result = new ArrayList<>();
        try {
            jaxbContext = JAXBContext.newInstance(Levels.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(path.getPath());
            Levels levels = (Levels) unmarshaller.unmarshal(file);
            result.addAll(levels.getLevelList());
        } catch (JAXBException e) {
            //TODO: Log error
            e.printStackTrace();
        }
        levels = result;
    }
}

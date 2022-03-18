package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.BossDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.boss.Boss;
import hu.unideb.inf.mathhelper.model.boss.Bosses;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BossDAOImpl implements BossDAO {

    private final LocationDAO locationDAO;

    private List<Boss> bosses;

    public BossDAOImpl(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    @Override
    public List<Boss> getBossList() {
        if (bosses == null) {
            loadBosses();
        }
        return bosses;
    }

    private void loadBosses() {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        List<Boss> result = new ArrayList<>();
        try {
            jaxbContext = JAXBContext.newInstance(Bosses.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(locationDAO.getBossesSystemFilePath().getPath());
            Bosses bosses = (Bosses) unmarshaller.unmarshal(file);
            result.addAll(bosses.getBossList());
        } catch (JAXBException e) {
            //TODO: Log error
            e.printStackTrace();
        }
        bosses = result;
    }
}

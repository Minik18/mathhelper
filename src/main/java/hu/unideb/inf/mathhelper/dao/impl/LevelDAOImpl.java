package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LevelDAO;

import java.util.Map;

public class LevelDAOImpl implements LevelDAO {

    private Map<Integer,Integer> levels;

    @Override
    public Map<Integer, Integer> getLevelSystem() {
        return levels;
    }

    @Override
    public void loadLevel() {
        //TODO: Do level read
    }
}

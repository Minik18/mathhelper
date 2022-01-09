package hu.unideb.inf.mathhelper.dao;

import java.util.Map;

public interface LevelDAO {

    Map<Integer, Integer> getLevelSystem();

    void loadLevel();

}

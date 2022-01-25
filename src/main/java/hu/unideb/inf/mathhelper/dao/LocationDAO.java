package hu.unideb.inf.mathhelper.dao;

import java.net.URL;

public interface LocationDAO {

    URL getQuestionFilePath(String fileName);

    URL getPictureFilePath(String fileName);

    URL getLevelSystemFilePath();

    URL getSceneFilePath(String fileName);

    URL getPaneFilePath(String fileName);

    String getTextFilePath(String fileName);

}

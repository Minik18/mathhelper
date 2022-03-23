package hu.unideb.inf.mathhelper.dao;

import java.io.InputStream;

public interface LocationDAO {

    String getQuestionFolderPath();

    String getQuestionPictureFilePath(String fileName);

    String getUiPictureFilePath(String fileName);

    InputStream getLevelSystemFilePath();

    String getSceneFilePath(String fileName);

    String getPaneFilePath(String fileName);

    String getTextFilePath(String fileName);

    String getDefaultSettingsFilePath();

    String getSettingsFilePath();

    String getCategoryFilePath();

    String getProfilePictureFilePath(String name);

    String getProfilePictureFolderPath();

    String getSampleQuestionPaneFilePath();

    InputStream getBossesSystemFilePath();

}

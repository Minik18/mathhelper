package hu.unideb.inf.mathhelper.dao;

import java.net.URL;

public interface LocationDAO {

    URL getQuestionFolderPath();

    String getQuestionPictureFilePath(String fileName);

    String getUiPictureFilePath(String fileName);

    URL getLevelSystemFilePath();

    URL getSceneFilePath(String fileName);

    String getPaneFilePath(String fileName);

    String getTextFilePath(String fileName);

    URL getDefaultSettingsFilePath();

    String getSettingsFilePath();

    URL getCategoryFilePath();

    String getProfilePictureFilePath(String name);

    String getProfilePictureFolderPath();

    String getSampleQuestionPaneFilePath();

    URL getBossesSystemFilePath();

}

package hu.unideb.inf.mathhelper.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "locations")
public class Location {

    @XmlElement(name = "levels")
    private String levelsFile;

    @XmlElement(name = "questions_folder")
    private String questionFolder;

    @XmlElement(name = "pictures_folder")
    private String picturesFolder;

    public String getLevelsFile() {
        return levelsFile;
    }

    public String getQuestionFolder() {
        return questionFolder;
    }

    public String getPicturesFolder() {
        return picturesFolder;
    }
}

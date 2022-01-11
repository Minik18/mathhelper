package hu.unideb.inf.mathhelper.model.level;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Levels {

    @XmlElement(name = "level")
    private List<Level> levelList;

    public List<Level> getLevelList() {
        return levelList;
    }
}

package hu.unideb.inf.mathhelper.model.level;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Levels {

    @XmlElement(name = "level")
    private List<Level> levelList;

    public List<Level> getLevelList() {
        return levelList;
    }
}

package hu.unideb.inf.mathhelper.model.boss;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement

public class Bosses {

    @XmlElement(name = "boss")
    private List<Boss> bossList;

    public List<Boss> getBossList() {
        return bossList;
    }

}

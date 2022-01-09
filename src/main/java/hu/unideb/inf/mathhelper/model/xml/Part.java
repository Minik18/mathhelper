package hu.unideb.inf.mathhelper.model.xml;

import jakarta.xml.bind.annotation.XmlEnumValue;

public enum Part {
    @XmlEnumValue("first")
    FIRST,
    @XmlEnumValue("second_a")
    SECOND_A,
    @XmlEnumValue("second_b")
    SECOND_B
}

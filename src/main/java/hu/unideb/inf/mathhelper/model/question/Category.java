package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Category {
    @XmlEnumValue("absolute")
    ABSOLUTE_VALUE_ROOT,
    @XmlEnumValue("equation")
    EQUATION_INEQUALITIES_SYSTEM_OF_EQUATION,
    @XmlEnumValue("simplification")
    SIMPLIFICATION,
    @XmlEnumValue("function")
    FUNCTION,
    @XmlEnumValue("exponential_logarithm")
    EXPONENTIAL_LOGARITHM,
    @XmlEnumValue("set")
    SET,
    @XmlEnumValue("combinatory")
    COMBINATORY,
    @XmlEnumValue("coordinate_geometry")
    COORDINATE_GEOMETRY,
    @XmlEnumValue("logic_graph")
    LOGIC_GRAPH,
    @XmlEnumValue("geometry")
    GEOMETRY,
    @XmlEnumValue("series")
    SERIES,
    @XmlEnumValue("statistic")
    STATISTIC,
    @XmlEnumValue("account")
    ACCOUNT_THEORY,
    @XmlEnumValue("terminometry")
    TERMINOMETRY,
    @XmlEnumValue("trigonometry")
    TRIGONOMETRY,
    @XmlEnumValue("probability")
    PROBABILITY
}

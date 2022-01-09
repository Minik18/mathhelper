package hu.unideb.inf.mathhelper.model.xml;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class SubQuestions {

    @XmlElement(name = "sub_question")
    private List<SubQuestion> subQuestionList;

    public List<SubQuestion> getSubQuestionList() {
        return subQuestionList;
    }
}

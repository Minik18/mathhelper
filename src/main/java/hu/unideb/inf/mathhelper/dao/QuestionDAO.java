package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.UnknownYearException;
import hu.unideb.inf.mathhelper.model.question.Question;

import java.util.List;
import java.util.Map;

public interface QuestionDAO {

    List<Question> loadQuestionsIntoList() throws QuestionFileNotFoundException;

}

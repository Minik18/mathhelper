package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.UnknownYearException;
import hu.unideb.inf.mathhelper.model.question.Question;

import java.util.List;
import java.util.Map;

public interface QuestionDAO {

    /***
     * Loads all questions from the given folder into a list.
     * @param path a string referring the folder where the files will be looked for.
     * @return a list of {@link hu.unideb.inf.mathhelper.model.question.Question} pairs.
     * @throws QuestionFileNotFoundException when there is no question file int the given folder.
     */
    List<Question> loadQuestionsIntoList(String path) throws QuestionFileNotFoundException;

    /***
     * Loads all questions from the given folder into a map, where the key is the year and a value is a list containing
     * the questions.
     * @param path a string referring the folder where the files will be looked for.
     * @return a {@link Map} of String and {@link List} of {@link hu.unideb.inf.mathhelper.model.question.Question} pairs.
     * @throws QuestionFileNotFoundException when there is no question file int the given folder.
     */
    Map<String,List<Question>> mapAllQuestions(String path) throws QuestionFileNotFoundException;

    /***
     * Loads questions from the given folder by the given year.
     * @param path a string referring the folder where the files will be looked for.
     * @param year a string referring to the given year in a format of, year_aut / year_may.
     * @return a list of {@link hu.unideb.inf.mathhelper.model.question.Question}.
     * @throws UnknownYearException when the given year is not found.
     * @throws QuestionFileNotFoundException when there is no question file int the given folder.
     */
    List<Question> loadQuestionsByYear(String path, String year) throws UnknownYearException,
            QuestionFileNotFoundException;

}

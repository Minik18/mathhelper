package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.UnknownYearException;
import hu.unideb.inf.mathhelper.model.question.Question;
import hu.unideb.inf.mathhelper.model.question.Root;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class QuestionDAOImpl implements QuestionDAO {

    private static final String REGEX = "20[0-2][0-9]_((aut)|(spr))";
    private static final int MAX_YEAR = 2021;
    private static final int MIN_YEAR = 2021;

    private List<Question> allList;
    private Map<String,List<Question>> questionMap;
    private List<Question> specificYearQuestion;
    private String lastQueriedYear;

    @Override
    public List<Question> loadQuestionsIntoList(String path) throws QuestionFileNotFoundException {
        if (questionMap == null) {
            File folder = checkFileExistence(path);
            allList = load(folder,"all");
        }
        return allList;
    }

    @Override
    public Map<String, List<Question>> mapAllQuestions(String path) throws QuestionFileNotFoundException {
        if (questionMap == null) {
            File folder = checkFileExistence(path);
            questionMap = loadMap(folder);
        }
        return questionMap;
    }

    @Override
    public List<Question> loadQuestionsByYear(String path, String year) throws UnknownYearException,
            QuestionFileNotFoundException {
        if (specificYearQuestion == null || !year.equals(lastQueriedYear)) {
            File folder = checkFileExistence(path);
            checkYearValidation(year);
            specificYearQuestion = load(folder,year);
            lastQueriedYear = year;
        }
        return specificYearQuestion;
    }

    private Map<String, List<Question>> loadMap(File folder) {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        Map<String,List<Question>> result = new HashMap<>();
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            for(File file : Objects.requireNonNull(folder.listFiles())) {
                Root root = (Root) unmarshaller.unmarshal(file);
                result.put(root.getId(),root.getQuestionsObject().getQuestionList());
            }
        } catch (JAXBException e) {
            //TODO: Log error
            e.printStackTrace();
        }
        return result;
    }

    private List<Question> load(File folder,String year) throws QuestionFileNotFoundException {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        List<Question> result = new ArrayList<>();
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
            unmarshaller = jaxbContext.createUnmarshaller();

            if (year.equals("all")) {
                for(File file : Objects.requireNonNull(folder.listFiles())) {
                    Root root = (Root) unmarshaller.unmarshal(file);
                    result.addAll(root.getQuestionsObject().getQuestionList());
                }
            } else {
                File xmlFile = new File(folder.getPath()+"\\"+year+".xml");
                Root root = (Root) unmarshaller.unmarshal(xmlFile);
                result.addAll(root.getQuestionsObject().getQuestionList());
            }
        } catch (JAXBException e) {
            //TODO: Log error
            e.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new QuestionFileNotFoundException("No file found for name: " + illegalArgumentException.getMessage());
        }
        return result;
    }

    private void checkYearValidation(String year) throws UnknownYearException {
        if (!(Pattern.matches(REGEX,year))) {
            throw new UnknownYearException("No year file found for year: " + year);
        }
        int actualYear = Integer.parseInt(year.split("_")[0]);
        if (!(actualYear >= MIN_YEAR && actualYear <= MAX_YEAR)) {
            throw new UnknownYearException("Enter year between " + MIN_YEAR + " and " + MAX_YEAR);
        }
    }

    private File checkFileExistence(String path) throws QuestionFileNotFoundException {
        File folder = new File(path);
        if (0 == Objects.requireNonNull(folder.listFiles()).length) {
            throw new QuestionFileNotFoundException("No question xml file found in the given path: " + path);
        } else {
            return folder;
        }
    }
}

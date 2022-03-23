package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.question.Question;
import hu.unideb.inf.mathhelper.model.question.Root;
import hu.unideb.inf.mathhelper.service.RunTypeTracker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class QuestionDAOImpl implements QuestionDAO {

    private List<Question> allList;

    private final LocationDAO locationDAO;
    private final RunTypeTracker runTypeTracker;

    public QuestionDAOImpl(LocationDAO locationDAO, RunTypeTracker runTypeTracker) {
        this.locationDAO = locationDAO;
        this.runTypeTracker = runTypeTracker;
    }


    @Override
    public List<Question> loadQuestionsIntoList() {
        if (allList == null) {
            allList = load();
        }
        return allList;
    }

    private List<Question> load() {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        List<Question> result = new ArrayList<>();
        try {
            jaxbContext = JAXBContext.newInstance(Root.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            if (runTypeTracker.isApplicationRunByJar()) {
                final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                        .replace("file:", "").replace("!/BOOT-INF/classes!", ""));
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    final String name = entry.getName();
                    if (name.startsWith("BOOT-INF/classes/questions/2")) {
                        InputStream inputStream = QuestionDAOImpl.class.getClassLoader().getResourceAsStream(name);
                        Root root = (Root) unmarshaller.unmarshal(inputStream);
                        result.addAll(root.getQuestionsObject().getQuestionList());
                    }
                }
                jar.close();
            } else {
                File folder = new File(locationDAO.getQuestionFolderPath().replace("file:", ""));
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    Root root = (Root) unmarshaller.unmarshal(file);
                    result.addAll(root.getQuestionsObject().getQuestionList());
                }
            }
        } catch (JAXBException | IOException e) {
            AppLogger.logError(e);
        }
        return result;
    }
}

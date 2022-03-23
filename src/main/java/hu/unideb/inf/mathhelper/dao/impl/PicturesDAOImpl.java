package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.PicturesDAO;
import hu.unideb.inf.mathhelper.exception.ImageNotFoundException;
import javafx.scene.image.Image;

import java.io.File;

public class PicturesDAOImpl implements PicturesDAO {

    @Override
    public Image loadPicture(String name) throws ImageNotFoundException{
        return checkFileValidation(name);
    }

    private Image checkFileValidation(String name) throws ImageNotFoundException {
        File file = new File(name);
        if (!file.exists()) {
            throw new ImageNotFoundException("Image with name " + name + " not found");
        }
        return new Image("file:"+file.getPath());
    }
}

package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.PicturesDAO;
import hu.unideb.inf.mathhelper.exception.ImageNotFoundException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PicturesDAOImpl implements PicturesDAO {

    @Override
    public Image loadPicture(String name) throws ImageNotFoundException {
        return checkFileValidation(name);
    }

    private Image checkFileValidation(String name) throws ImageNotFoundException {
        File file = new File(name);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new ImageNotFoundException("The given image cannot be find at: " + name);
        }
    }
}
